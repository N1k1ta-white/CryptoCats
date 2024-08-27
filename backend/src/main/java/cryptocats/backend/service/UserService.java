package cryptocats.backend.service;

import cryptocats.backend.entity.User;
import cryptocats.backend.exception.NotValidQueryException;
import cryptocats.backend.exception.UserAlreadyExistsException;
import cryptocats.backend.repository.UserRepository;
import cryptocats.backend.util.TelegramQueryValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private TelegramQueryValidator validator;

    public Optional<User> authUser(User user, String query) throws NoSuchAlgorithmException, InvalidKeyException {
        if (validator.validate(query)) {
            return userRepository.findById(user.getId());
        }
        throw new NotValidQueryException("Not valid query");
    }

    public User registerUser(User user) {
        if (!userRepository.existsUserByIdIs(user.getId())) {
            user.setCapital(0L);
            user.setLastOpenedTime(0L);
            userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException("User already exists!");
        }
        return user;
    }
}
