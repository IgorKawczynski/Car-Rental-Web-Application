package pl.igokaw.user.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igokaw.global.dto.ErrorsListDto;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class UserLoginResponseDto implements Serializable {

    private String sessionId;
    private ErrorsListDto errorsListDto;

    public UserLoginResponseDto(ErrorsListDto errorsListDto) {
        this.errorsListDto = errorsListDto;
    }
    public void addToErrorList(String error){
        this.errorsListDto.add(error);
    }

}
