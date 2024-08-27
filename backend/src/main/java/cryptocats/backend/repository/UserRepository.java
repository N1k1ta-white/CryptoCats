package cryptocats.backend.repository;

import cryptocats.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByIdIs(Long id);
}
