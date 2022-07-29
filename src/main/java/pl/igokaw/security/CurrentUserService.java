package pl.igokaw.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.igokaw.user.UserEntity;
import pl.igokaw.user.UserRepository;
import pl.igokaw.user.value_objects.EmailValidator;

@Service
public class CurrentUserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public CurrentUser loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity user = userRepository.findUserByEmail(new EmailValidator(username));
        if (user != null) {
            final CurrentUser currentUser = new CurrentUser();
            currentUser.setUsername(user.getEmail().toString());
            currentUser.setPassword(user.getPassword().toString());

            return currentUser;
        }
        else
            throw new UsernameNotFoundException("Failed to find user with email: " + username);
    }
}
