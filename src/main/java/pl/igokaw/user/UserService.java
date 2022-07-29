package pl.igokaw.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.igokaw.user.dto.UserResponseDto;
import pl.igokaw.user.value_objects.EmailValidator;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponseDto> getAllUsers() {
        var users = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return userMapper.fromUserEntityListToUserResponseList(users);
    }

    public UserEntity getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("UserEntity with id: " + id + " does not exist!"));
    }

    public UserResponseDto getUserById(Long id) {
        var user = getUser(id);
        return userMapper.fromUserEntityToUserResponseDto(user);
    }

    public UserResponseDto updateUserEmail(Long id, String email) {
        var user = getUser(id);
        user.changeEmail(email);
        userRepository.save(user);
        return userMapper.fromUserEntityToUserResponseDto(user);
    }

    public String deleteUserById(Long id) {
        var user = getUser(id);
        userRepository.delete(user);
        return "User with id: " + id + " deleted successfully!";
    }

    public UserResponseDto getCurrentLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            UserEntity user = userRepository.findUserByEmail(new EmailValidator(email));
            return userMapper.fromUserEntityToUserResponseDto(user);
        }
        return null;
    }

}
