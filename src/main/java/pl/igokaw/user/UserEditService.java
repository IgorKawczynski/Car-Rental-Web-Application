package pl.igokaw.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.igokaw.global.dto.ErrorsListDto;
import pl.igokaw.interfaces.UserValidator;
import pl.igokaw.user.dto.UserEditRequestDto;
import pl.igokaw.user.value_objects.EmailValidator;

import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserEditService implements UserValidator {

    private final UserRepository userRepository;

    public UserEntity getUserByEmail(String email){
        EmailValidator emailValidator = new EmailValidator(email);
        if(userRepository.findUserByEmail(emailValidator) == null){
            throw new NoSuchElementException("UserEntity with email: " + email + " does not exist!");
        }
        return  userRepository.findUserByEmail(emailValidator);
    }

    public ErrorsListDto updateUserWithPutMapping(String email, UserEditRequestDto updatedUser) {

        ErrorsListDto errorsListDto = new ErrorsListDto( new ArrayList<>() );

        if ( !emailContainsAtSign(updatedUser.email()) ) {
            errorsListDto.add("Email must contains '@' sign!");
        }
        if ( !loginIsValidLength(updatedUser.login()) ) {
            errorsListDto.add("Login must be at least 7 chars long!");
        }
        if ( !nameContainsValidSigns(updatedUser.firstName(), updatedUser.secondName()) ) {
            errorsListDto.add("First name and second name must contains only letters!");
        }
        if ( !isAnAdult(updatedUser.pesel())){
            errorsListDto.add("You need to be an adult to use our service!");
        }
        if( errorsListDto.isListOfErrorsEmpty() ) {

            var userToBeChanged = getUserByEmail(email);
            userToBeChanged.changeEmail(updatedUser.email());
            userToBeChanged.changeFirstName(updatedUser.firstName());
            userToBeChanged.changeSecondName(updatedUser.secondName());
            userToBeChanged.changePhoneNumber(updatedUser.phoneNumber());
            userToBeChanged.changeLogin(updatedUser.login());
            userToBeChanged.changePesel(updatedUser.pesel());
            userRepository.save(userToBeChanged);
        }
        return errorsListDto;
    }
    public UserEditRequestDto getUserEditRequestDtoByEmail( String email){
        UserEntity userEntity = getUserByEmail(email);
        return UserEditRequestDto.builder()
                .login(userEntity.getLogin().toString())
                .firstName(userEntity.getFirstName().toString())
                .secondName(userEntity.getSecondName().toString())
                .phoneNumber(userEntity.getPhoneNumber().toString())
                .email(userEntity.getEmail().toString())
                .pesel(userEntity.getPesel().toString())
                .build();
    }
}
