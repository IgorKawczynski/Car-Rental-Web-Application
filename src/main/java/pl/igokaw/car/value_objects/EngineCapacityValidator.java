package pl.igokaw.car.value_objects;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class EngineCapacityValidator {

    @Column
    private BigDecimal engineCapacity;

    public EngineCapacityValidator(BigDecimal engineCapacity) {
        if(Objects.isNull(engineCapacity))
            throw new IllegalStateException("ENGINE CAPACITY CANNOT BE NULL !!");
        if(!isCapacityValid(engineCapacity))
            throw new IllegalStateException("ENGINE CAPACITY MUST BE BETWEEN 0.5 AND 8 LITERS !!");
        this.engineCapacity = engineCapacity;
    }

    public boolean isCapacityValid(BigDecimal engineCapacity){
        return engineCapacity.compareTo(BigDecimal.valueOf(0.5d)) >= 0 &&
                engineCapacity.compareTo(BigDecimal.valueOf(8d)) <= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EngineCapacityValidator that = (EngineCapacityValidator) o;
        return engineCapacity.equals(that.engineCapacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(engineCapacity);
    }

    public BigDecimal toBigDecimal(){
        return engineCapacity;
    }

}
