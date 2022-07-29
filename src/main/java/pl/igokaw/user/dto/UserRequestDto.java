package pl.igokaw.user.dto;

import lombok.Builder;


public record UserRequestDto(String login, String firstName, String secondName,
                             String password, String phoneNumber, String email,
                             String pesel) {

    @Builder public UserRequestDto {}

}
