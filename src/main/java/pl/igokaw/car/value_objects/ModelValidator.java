package pl.igokaw.car.value_objects;

import lombok.NoArgsConstructor;
import pl.igokaw.interfaces.Validator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class ModelValidator implements Validator {

    private static final String MODEL_NAME_REGEX = "[a-z A-Z\\d_.-]+";
    @Column
    private String model;

    public ModelValidator(String model) {
        if(Objects.isNull(model)) {
            throw new IllegalArgumentException("MODEL CANNOT BE NULL !!");
        }
        if(!containsValidCharacters(model, MODEL_NAME_REGEX)) {
            throw new IllegalStateException("MODEL NAME MAY CONTAIN ONLY LETTERS, " +
                    "NUMBERS, DOTS, DASHES AND UNDERSCORE!!");
        }
        if(!isValidLength(model, 2, 30)){
            throw new IllegalStateException("MODEL NAME MUST BE BETWEEN 2 AND 30 CHARACTERS !!");
        }

        this.model = model;
    }

    @Override
    public String toString() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelValidator modelValidator1 = (ModelValidator) o;
        return model.equals(modelValidator1.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model);
    }
}
