package pl.igokaw.user.value_objects;

import lombok.NoArgsConstructor;
import pl.igokaw.interfaces.Validator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class EmailValidator implements Validator {
    private static final String EMAIL_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+" +
            "(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]" +
            "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)" +
            "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
            "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\" +
            "x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";
//EmailValidator pattern from EMAILREGEXCOM

    @Column
    private String email;

    public EmailValidator(String email) {
        if ( Objects.isNull(email) )
            throw new IllegalArgumentException("Email can't be null!");
        if ( !isValidLength(email, 5, 80) )
            throw new IllegalArgumentException("Email must be between 8 to 50 characters length!");
        if ( !containsValidCharacters(email, EMAIL_PATTERN) )
            throw new IllegalArgumentException("Email may contain only letters, digits, and '@' '.' signs!");
        this.email = email;
    }

    @Override
    public boolean containsValidCharacters(String stringToCheck, String pattern) {
        stringToCheck = stringToCheck.toLowerCase();
        return Validator.super.containsValidCharacters(stringToCheck, pattern);
    }

    @Override
    public String toString() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailValidator emailValidator1 = (EmailValidator) o;
        return email.equals(emailValidator1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

}
