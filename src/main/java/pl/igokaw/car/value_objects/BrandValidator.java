package pl.igokaw.car.value_objects;

import lombok.NoArgsConstructor;
import pl.igokaw.interfaces.Validator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor
@Embeddable
public class BrandValidator implements Validator {

    private static final String BRAND_NAME_REGEX = "[a-zA-Z\\d]+";
    @Column
    private String brand;

    public BrandValidator(String brand) {
        if(Objects.isNull(brand))
            throw new IllegalArgumentException("BRAND CANNOT BE NULL !!");
        if(!containsValidCharacters(brand, BRAND_NAME_REGEX))
            throw new IllegalStateException("BRAND NAME MAY CONTAIN ONLY LETTERS OR NUMBERS !!");
        if(!isValidLength(brand, 1, 20))
            throw new IllegalStateException("BRAND NAME MUST BE BETWEEN 1 AND 20 CHARACTERS !!");
        this.brand = brand;
    }

    @Override
    public String toString() {
        return brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandValidator brandValidator1 = (BrandValidator) o;
        return brand.equals(brandValidator1.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand);
    }
}
