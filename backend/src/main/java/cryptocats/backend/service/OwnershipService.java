package cryptocats.backend.service;

import cryptocats.backend.entity.Ownership;
import cryptocats.backend.entity.embedded.OwnershipId;
import cryptocats.backend.repository.OwnershipRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OwnershipService {

    private OwnershipRepository ownershipRepository;

    public void saveOwnership(Ownership ownership) {
        ownershipRepository.save(ownership);
    }

    public Optional<Ownership> findOwnershipByOwnershipId(OwnershipId ownershipId) {
        return ownershipRepository.findOwnershipByOwnershipId(ownershipId);
    }

    public Page<Ownership> findOwnershipByUserId(Long userId, Pageable pageable) {
        return ownershipRepository.findByOwnershipIdUserId(userId, pageable);
    }
}
