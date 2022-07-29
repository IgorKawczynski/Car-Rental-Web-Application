package pl.igokaw.user;

import org.springframework.stereotype.Component;
import pl.igokaw.user.dto.UserEditRequestDto;
import pl.igokaw.user.dto.UserRequestDto;
import pl.igokaw.user.dto.UserResponseDto;
import pl.igokaw.user.value_objects.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDto fromUserEntityToUserResponseDto(UserEntity userEntity){
        return UserResponseDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName().toString())
                .secondName(userEntity.getSecondName().toString())
                .phoneNumber(userEntity.getPhoneNumber().toString())
                .email(userEntity.getEmail().toString())
                .build();
    }

    public UserEditRequestDto fromUserEntityToUserEditRequestDto(UserEntity userEntity) {
        return UserEditRequestDto.builder()
                .login(userEntity.getLogin().toString())
                .firstName(userEntity.getFirstName().toString())
                .secondName(userEntity.getSecondName().toString())
                .phoneNumber(userEntity.getPhoneNumber().toString())
                .email(userEntity.getEmail().toString())
                .pesel(userEntity.getPesel().toString())
                .build();
    }

    public List<UserResponseDto> fromUserEntityListToUserResponseList(List<UserEntity> userEntityList){
        return userEntityList.stream()
                .map(this::fromUserEntityToUserResponseDto)
                .collect(Collectors.toList());
    }

    public UserEntity fromUserRequestDtoToUserEntity(UserRequestDto userRequestDto){
        return UserEntity.builder()
                .login(new LoginValidator(userRequestDto.login()))
                .firstName(new NameValidator(userRequestDto.firstName()))
                .secondName(new NameValidator(userRequestDto.secondName()))
                .email(new EmailValidator(userRequestDto.email()))
                .password(new PasswordValidator(userRequestDto.password()))
                .phoneNumber(new PhoneNumberValidator(userRequestDto.phoneNumber()))
                .pesel(new PeselValidator(userRequestDto.pesel()))
                .build();
    }

}
