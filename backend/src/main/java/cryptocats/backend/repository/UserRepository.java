package cryptocats.backend.repository;

import cryptocats.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByIdIs(Long id);

    Page<User> findByInvitedBy(Long invitedBy, Pageable pageable);
}
