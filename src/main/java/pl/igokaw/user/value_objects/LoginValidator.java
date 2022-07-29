package pl.igokaw.user.value_objects;

import lombok.NoArgsConstructor;
import pl.igokaw.interfaces.Validator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class LoginValidator implements Validator {
    private static final String ENGLISH_LETTERS_NUMBERS_DOT_UNDERSCORE_DASH = "[a-zA-Z\\d._-]+";

    @Column
    private String login;

    public LoginValidator(String login) {
        if (Objects.isNull(login))
            throw new IllegalStateException("Login cant be null!");
        if ( !isValidLength(login, 3, 28) )
            throw new IllegalStateException("Login must be between 3 and 28 characters length!");
        if ( !containsValidCharacters(login, ENGLISH_LETTERS_NUMBERS_DOT_UNDERSCORE_DASH) )
            throw new IllegalStateException("Login may contain only english letters, numbers, dots, underscores or dashes!");
        this.login = login;
    }

    @Override
    public String toString() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginValidator loginValidator1 = (LoginValidator) o;
        return login.equals(loginValidator1.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

}
