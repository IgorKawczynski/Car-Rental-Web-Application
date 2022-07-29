package pl.igokaw.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.igokaw.global.dto.ErrorsListDto;
import pl.igokaw.user.dto.UserLoginDto;
import pl.igokaw.user.dto.UserLoginResponseDto;
import pl.igokaw.user.dto.UserRequestDto;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserWebController {

    private final UserLoginService userLoginService;
    private final UserRegistrationService userRegistrationService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody UserLoginDto user) {
        return ResponseEntity.ok(userLoginService.login(user));
    }
    @PostMapping("/registration")
    public ErrorsListDto create(@RequestBody UserRequestDto user) {
        return userRegistrationService.register(user);
    }
}
