package cryptocats.backend.repository;

import cryptocats.backend.entity.Ownership;
import cryptocats.backend.entity.embedded.OwnershipId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnershipRepository extends JpaRepository<Ownership, Integer> {
    Optional<Ownership> findOwnershipByOwnershipId(OwnershipId ownershipId);

    Page<Ownership> findByOwnershipIdUserId(Long userId, Pageable pageable);
}
