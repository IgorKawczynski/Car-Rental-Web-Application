package pl.igokaw.user.value_objects;

import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.igokaw.interfaces.Validator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class PasswordValidator implements Validator {

    private static final String ENGLISH_LETTERS_NUMBERS_SPECIAL_CHARACTERS = "[\\x21-\\x7E]+";
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Column
    private String password;

    public PasswordValidator(String password) {
        if ( Objects.isNull(password) )
            throw new IllegalStateException("Password cant be null!");
        if ( !isValidLength(password, 7, 28) )
            throw new IllegalStateException("Password must be between 7 and 28 characters length!");
        if ( !containsValidCharacters(password, ENGLISH_LETTERS_NUMBERS_SPECIAL_CHARACTERS) )
            throw new IllegalStateException("Password may contain only english letters, numbers and special characters!");
        password = encryptPassword(password);
        this.password = password;
    }

    private String encryptPassword(String password){
        return this.password = passwordEncoder.encode(password);
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordValidator passwordValidator1 = (PasswordValidator) o;
        return password.equals(passwordValidator1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

}