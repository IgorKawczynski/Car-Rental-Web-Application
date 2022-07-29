package pl.igokaw.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.igokaw.car.dto.CarDto;
import pl.igokaw.car.exception.CarNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMapper carMapper;

    public CarDto addCar(CarEntity carEntity) {
        carRepository.save(carEntity);
        return carMapper.mapCarToCarDto(carEntity);
    }

    public CarEntity getCarEntityById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car with id " + id + " was not found. "));
    }

    public CarDto findCarById(Long id) {
        CarEntity carEntity = getCarEntityById(id);
        return carMapper.mapCarToCarDto(carEntity);
    }

    public CarDto updateCar(Long id, CarEntity carEntity) {
        var car = getCarEntityById(id);
        carRepository.save(carEntity);
        return carMapper.mapCarToCarDto(carEntity);
    }

    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    public List<CarDto> getAllCars(){
        List<CarEntity> carEntityList = carRepository.findAll();
        return carMapper.mapCarListToCarListDto(carEntityList);
    }

}
