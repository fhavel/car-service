package cz.frantisekhavel.carservice;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cz.frantisekhavel.carservice.car.Car;
import cz.frantisekhavel.carservice.car.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CarServiceApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CarRepository carRepository;

    private RequestPostProcessor jwtWithRoles(final String... roles) {
        return jwt().authorities(Stream.of(roles).map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList()));
    }

    @Test
    void getCarsReturnsEmptyListWhenNoCarsFound() throws Exception {
        carRepository.deleteAll();
        mockMvc.perform(get("/cars").with(jwtWithRoles("CAR_READ"))).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void createCarReturnsCreatedStatus() throws Exception {
        mockMvc.perform(post("/cars").with(jwtWithRoles("CAR_WRITE"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + UUID.randomUUID() + "\"}")).andExpect(status().isCreated());
    }

    @Test
    void createCarReturnsConflictStatusWhenCarAlreadyExists() throws Exception {
        final var carName = UUID.randomUUID().toString();
        carRepository.save(new Car(carName));

        mockMvc.perform(post("/cars").with(jwtWithRoles("CAR_WRITE"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + carName + "\"}")).andExpect(status().isConflict());
    }

    @Test
    void createCarReturnsForbiddenStatusWhenUnsufficientRoleApplied() throws Exception {
        mockMvc.perform(post("/cars").with(jwtWithRoles("CAR_READ"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"" + UUID.randomUUID() + "\"}")).andExpect(status().isForbidden());
    }

    @Test
    void getCarByIdReturnsNotFoundStatusWhenCarDoesNotExist() throws Exception {
        mockMvc.perform(get("/cars/99999").with(jwtWithRoles("CAR_READ"))).andExpect(status().isNotFound());
    }
}
