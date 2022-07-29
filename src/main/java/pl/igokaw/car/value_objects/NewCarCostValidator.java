package pl.igokaw.car.value_objects;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class NewCarCostValidator {

    @Column
    private Long newCarCost;

    public NewCarCostValidator(Long newCarCost) {
        if(Objects.isNull(newCarCost))
            throw new IllegalStateException("CAR COST CANNOT BE NULL !!");
        if(!isCostValid(newCarCost))
            throw new IllegalStateException("CAR COST MUST BE BETWEEN 0 AND 500 MILION !!");
        this.newCarCost = newCarCost;
    }

    public boolean isCostValid(Long newCarCost){
        return newCarCost >= 0 && newCarCost <= 500000000;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewCarCostValidator that = (NewCarCostValidator) o;
        return newCarCost.equals(that.newCarCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newCarCost);
    }

    public Long toLong(){
        return newCarCost;
    }
}
