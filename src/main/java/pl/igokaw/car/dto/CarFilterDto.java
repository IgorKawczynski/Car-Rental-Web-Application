package pl.igokaw.car.dto;

import lombok.Builder;
import pl.igokaw.car.enums.BodyTypeEnum;
import pl.igokaw.car.enums.TypeOfFuelEnum;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record CarFilterDto (String brand, String model, BigDecimal engineCapacity,
                            BodyTypeEnum bodyType, TypeOfFuelEnum typeOfFuel, Integer productionYear,
                            LocalDate freeFrom, LocalDate freeTo){

}
