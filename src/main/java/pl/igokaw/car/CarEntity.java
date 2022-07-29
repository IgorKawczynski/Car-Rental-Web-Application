package pl.igokaw.car;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.igokaw.car.enums.BodyTypeEnum;
import pl.igokaw.car.enums.TypeOfFuelEnum;
import pl.igokaw.car.value_objects.*;
import pl.igokaw.global.BasicEntity;
import pl.igokaw.reservation.ReservationEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "CARS")
public class CarEntity extends BasicEntity {

    @Embedded
    private BrandValidator brand;

    @Embedded
    private ModelValidator model;

    @OneToMany(mappedBy = "carId", fetch = FetchType.LAZY)
    private List<ReservationEntity> reservationEntity;

    @Embedded
    private EngineCapacityValidator engineCapacity;

    @Enumerated(EnumType.STRING)
    private BodyTypeEnum bodyType;

    @Enumerated(EnumType.STRING)
    private TypeOfFuelEnum typeOfFuel;

    @Embedded
    private NewCarCostValidator newCarCost;

    @Embedded
    private ProductionYearValidator productionYear;

}
