package pl.igokaw.user.value_objects;

import lombok.NoArgsConstructor;
import pl.igokaw.interfaces.Validator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class PeselValidator implements Validator {

    private static final String DIGITS = "[0-9]+";

    @Column
    private String pesel;

    public PeselValidator(String pesel) {
        if ( Objects.isNull(pesel) )
            throw new IllegalArgumentException("Pesel can't be null!");
        if ( !isValidLength(pesel, 11, 11) )
            throw new IllegalStateException("Pesel must be 11 digits length!");
        if ( !containsValidCharacters(pesel, DIGITS) )
            throw new IllegalArgumentException("Pesel may contain only digits!");
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeselValidator peselValidator1 = (PeselValidator) o;
        return pesel.equals(peselValidator1.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }

}
