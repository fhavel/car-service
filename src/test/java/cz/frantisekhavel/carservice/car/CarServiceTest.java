package cz.frantisekhavel.carservice.car;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import cz.frantisekhavel.carservice.car.exception.CarNotFoundException;
import cz.frantisekhavel.carservice.car.exception.DuplicateCarException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {
    private static final CarDto CAR_DTO_1 = new CarDto("Toyota", Instant.now());
    private static final CarDto CAR_DTO_2 = new CarDto("Renault", Instant.now());

    @Mock
    private Car car1;
    @Mock
    private Car car2;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @Test
    void getAllReturnsListOfCars() {
        when(car1.getCreated()).thenReturn(CAR_DTO_1.created());
        when(car1.getName()).thenReturn(CAR_DTO_1.name());
        when(car2.getCreated()).thenReturn(CAR_DTO_2.created());
        when(car2.getName()).thenReturn(CAR_DTO_2.name());

        when(carRepository.findAll()).thenReturn(List.of(car1, car2));

        final List<CarDto> actual = carService.getAll();

        assertThat(actual).containsExactly(CAR_DTO_1, CAR_DTO_2);
    }

    @Test
    void findByIdReturnsCar() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car1));

        final CarDto actual = carService.getById(1L);

        assertThat(actual).isEqualTo(CAR_DTO_1);
    }

    @Test
    void getByIdThrowsCarNotFoundExceptionWhenCarDoesntExist() {
        when(carRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> carService.getById(99L));
    }

    @Test
    void createReturnsId() {
        when(carRepository.save(new Car(CAR_DTO_1.name()))).thenReturn(car1);

        when(car1.getId()).thenReturn(1L);

        assertEquals(1L, carService.create(CAR_DTO_1));
    }

    @Test
    void createThrowsDuplicateCarExceptionWhenCarAlreadyExists() {
        when(car1.getId()).thenReturn(1L);
        when(carRepository.save(car1)).thenThrow(new DataIntegrityViolationException("duplicate car"));

        assertThrows(DuplicateCarException.class, () -> carService.create(CAR_DTO_1));
    }
}
