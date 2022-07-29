package pl.igokaw.user.dto;

import lombok.Builder;

public record UserResponseDto(Long id, String firstName, String secondName,
                              String phoneNumber, String email) {

    @Builder public UserResponseDto {}

}
