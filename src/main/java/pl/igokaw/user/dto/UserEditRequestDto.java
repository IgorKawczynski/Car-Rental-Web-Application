package pl.igokaw.user.dto;

import lombok.Builder;


public record UserEditRequestDto(String login, String firstName, String secondName, String phoneNumber, String email,
                             String pesel) {

    @Builder public UserEditRequestDto {}

}
