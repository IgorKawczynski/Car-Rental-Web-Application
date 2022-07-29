package pl.igokaw.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.igokaw.car.dto.CarDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<CarDto> getAllCars (){
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDto getCarById (@PathVariable("id") Long id){
        return carService.findCarById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CarDto addCar(@RequestBody CarEntity carEntity){
        return carService.addCar(carEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @RequestBody CarEntity carEntity){
        CarDto updatedCar = carService.updateCar(id, carEntity);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("HEADER V1",
                        "Car with id " + carEntity.getId() + " has been updated successfully");
        return new ResponseEntity<>(updatedCar, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarDto> deleteCar(@PathVariable("id") Long id){
        carService.deleteCarById(id);
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("HEADER V1",
                "Car with id " + id + " has been successfully deleted ");
        return new ResponseEntity<>(httpHeaders, HttpStatus.ACCEPTED);
    }

}
