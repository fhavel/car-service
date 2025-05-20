package cz.frantisekhavel.carservice.car;

import java.util.List;
import jakarta.validation.Valid;

import cz.frantisekhavel.carservice.car.exception.CarNotFoundException;
import cz.frantisekhavel.carservice.car.exception.DuplicateCarException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarDto> getAll() {
        return carRepository.findAll().stream().map(car -> new CarDto(car.getName(), car.getCreated())).toList();
    }

    public CarDto getById(final long id) {
        return carRepository.findById(id).map(car -> new CarDto(car.getName(), car.getCreated())).orElseThrow(CarNotFoundException::new);
    }

    public long create(@Valid final CarDto car) {
        try {
            return carRepository.save(new Car(car.name())).getId();
        } catch (final DataIntegrityViolationException e) {
            throw new DuplicateCarException(e, car.name());
        }
    }
}
