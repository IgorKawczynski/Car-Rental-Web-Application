package pl.igokaw.car;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.igokaw.car.dto.CarDto;
import pl.igokaw.car.enums.BodyTypeEnum;
import pl.igokaw.car.enums.TypeOfFuelEnum;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CarWebController {

    private final CarFilterService carFilterService;

    // zmienic na POST i ustawic requestBody
    @GetMapping(value = "/filter/cars", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CarDto> getAllCarsWithFilters(@RequestParam(required = false) String brand, @RequestParam(required = false) String model,
                                              @RequestParam(required = false) BigDecimal engineCapacity, @RequestParam(required = false) BodyTypeEnum bodyType,
                                              @RequestParam(required = false) TypeOfFuelEnum typeOfFuel, @RequestParam(required = false) Integer productionYear,
                                              @RequestParam(required = false) String freeFrom, @RequestParam(required = false) String freeTo) {

        var carFilterDto = carFilterService.getCarFilterDtoFromParams(brand, model, engineCapacity, bodyType, typeOfFuel, productionYear, freeFrom, freeTo);
        return carFilterService.getFilteredCars(carFilterDto);
    }
}
