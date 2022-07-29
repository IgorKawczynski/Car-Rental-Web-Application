package pl.igokaw.car.dto;


import lombok.Builder;
import pl.igokaw.car.enums.BodyTypeEnum;
import pl.igokaw.car.enums.StatusEnum;
import pl.igokaw.car.enums.TypeOfFuelEnum;

import java.math.BigDecimal;

@Builder
public record CarDto(Long id, String brand, String model,
                     BigDecimal engineCapacity, BodyTypeEnum bodyTypeEnum,
                     TypeOfFuelEnum typeOfFuelEnum, BigDecimal pricePerDayRent,
                     StatusEnum status, Integer productionYear, Long newCarCost) {



}
