package pl.igokaw.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.igokaw.global.dto.ErrorsListDto;
import pl.igokaw.interfaces.UserValidator;
import pl.igokaw.user.dto.UserRequestDto;
import pl.igokaw.user.value_objects.EmailValidator;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserRegistrationService implements UserValidator {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ErrorsListDto register(UserRequestDto userRequestDto) {

        ErrorsListDto errorsListDto = new ErrorsListDto( new ArrayList<>() );

        if ( !emailContainsAtSign(userRequestDto.email()) ) {
            errorsListDto.add("Email must contains '@' sign!");
        }
        if ( !isEmailUnique(userRequestDto.email()) ) {
            errorsListDto.add("User with this email already exists!");
        }
        if ( !passwordIsValidLength(userRequestDto.password()) ) {
            errorsListDto.add("Password must be at least 7 chars long!");
        }
        if ( !loginIsValidLength(userRequestDto.login()) ) {
            errorsListDto.add("Login must be at least 7 chars long!");
        }
        if ( !nameContainsValidSigns(userRequestDto.firstName(), userRequestDto.secondName()) ) {
            errorsListDto.add("First name and second name must contains only letters!");
        }
        if ( !isAnAdult(userRequestDto.pesel())){
            errorsListDto.add("You need to be an adult to use our service!");
        }
        if( errorsListDto.isListOfErrorsEmpty() ) {
            var userEntity = userMapper.fromUserRequestDtoToUserEntity(userRequestDto);
            userRepository.save(userEntity);
        }
        return errorsListDto;
    }

    public boolean isEmailUnique(String email){
        final UserEntity user = userRepository.findUserByEmail(new EmailValidator(email));
        return user == null;
    }
}
