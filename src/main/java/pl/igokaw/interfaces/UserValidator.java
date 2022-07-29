package pl.igokaw.interfaces;

import java.time.LocalDate;

public interface UserValidator {
    static final String POLISH_ALPHABET = "[a-zA-Z-\\p{IsAlphabetic}]+";

    default Boolean loginIsValidLength(String login) {
        return login != null && login.length() > 6;
    }

    default Boolean emailContainsAtSign(String email) {
        return email != null && email.contains("@");
    }

    default Boolean passwordIsValidLength(String password) {
        return password != null && password.length() > 6;
    }

    default Boolean nameContainsValidSigns(String firstName, String secondName) {
        return firstName != null && firstName.matches(POLISH_ALPHABET) &&
                secondName != null && secondName.matches(POLISH_ALPHABET);
    }

    default Boolean isAnAdult(String pesel){
        return pesel != null && LocalDate.now().getYear() - yearOfBirth(pesel) >= 18;
    }

    default int yearOfBirth (String pesel){
        int lastTwoDigitsYearOfBirth = Integer.parseInt(pesel.substring(0,2));
        int codedCentury = Integer.parseInt(pesel.substring(2,4));
        int century = 1800;

        if (codedCentury < 13) century = 1900;
        if (codedCentury > 12 && codedCentury < 33) century = 2000;

        return century + lastTwoDigitsYearOfBirth;
    }

}
