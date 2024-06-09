package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.User;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.repository.UserRepository;
import aminurdev.com.backend.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(aminurdev.com.backend.domain.request.User userRequest) {

        try {

            User user = new User();

            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

            user = userRepository.save(user);

            return user;

        }catch (Exception exception){

            throw new CustomException("Error while register user: " + exception.getMessage(), exception);
        }
    }
}
