package cryptocats.backend.service;

import cryptocats.backend.entity.Cat;
import cryptocats.backend.entity.Ownership;
import cryptocats.backend.entity.User;
import cryptocats.backend.entity.embedded.OwnershipId;
import cryptocats.backend.exception.NotFoundEntityException;
import cryptocats.backend.exception.NotValidQueryException;
import cryptocats.backend.exception.UserAlreadyExistsException;
import cryptocats.backend.repository.UserRepository;
import cryptocats.backend.util.TelegramQueryValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private TelegramQueryValidator validator;
    private OwnershipService ownershipService;
    private static final int INCREMENT_BY = 1;

    public Optional<User> authUser(User user, String query) {
        if (validator.validate(query)) {
            return userRepository.findById(user.getId());
        }

        throw new NotValidQueryException("Not valid query");
    }

    public User registerUser(User user) {
        if (!userRepository.existsUserByIdIs(user.getId())) {
            user.setCapital(0L);
            user.setLastOpenedTime(Instant.EPOCH);
            userRepository.save(user);
            return user;
        }

        throw new UserAlreadyExistsException("User already exists!");
    }

    public Page<User> getAllReferrals(Pageable pageable, Long ownerId) {
        return userRepository.findByInvitedBy(ownerId, pageable);
    }

    public void giveCatToUser(User user, Cat cat) {
        OwnershipId ownershipId = new OwnershipId(user.getId(), cat.getId());
        Ownership ownership = ownershipService.findOwnershipByOwnershipId(ownershipId)
                .or(() -> Optional.of(new Ownership(ownershipId, cat, user, 0L, Instant.now())))
                .get();
        ownership.setAmount(ownership.getAmount() + INCREMENT_BY);
        ownershipService.saveOwnership(ownership);
    }

    public void updateOpenedTime(User user) {
        user.setLastOpenedTime(Instant.now());
        userRepository.save(user);
    }

    public User findUserById(String userId) {
        Long id = Long.parseLong(userId);
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("User not found"));
    }
}
