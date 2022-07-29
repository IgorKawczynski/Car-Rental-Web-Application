package pl.igokaw.car;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.igokaw.car.dto.CarDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CarMapper {

    private final CarFieldsService carFieldsService;
    public CarDto mapCarToCarDto(CarEntity carEntity) {
        return CarDto.builder()
                .id(carEntity.getId())
                .brand(carEntity.getBrand().toString())
                .model(carEntity.getModel().toString())
                .engineCapacity(carEntity.getEngineCapacity().toBigDecimal())
                .productionYear(carEntity.getProductionYear().toInteger())
                .typeOfFuelEnum(carEntity.getTypeOfFuel())
                .pricePerDayRent(carFieldsService.setInitialPrice(carEntity.getId()))
                .bodyTypeEnum(carEntity.getBodyType())
                .status(carFieldsService.getStatus(carEntity.getId()))
                .newCarCost(carEntity.getNewCarCost().toLong())
                .build();
    }

    public List<CarDto> mapCarListToCarListDto(List<CarEntity> carEntities) {
        return carEntities
                .stream()
                .map(this::mapCarToCarDto)
                .collect(Collectors.toList());
    }

}
