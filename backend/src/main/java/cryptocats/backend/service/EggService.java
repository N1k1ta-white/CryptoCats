package cryptocats.backend.service;

import cryptocats.backend.entity.Egg;
import cryptocats.backend.exception.NotFoundEntityException;
import cryptocats.backend.repository.EggRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EggService {

    private EggRepository eggRepository;

    public Egg findById(Long id) {
        return eggRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException("Egg with appropriate ID wasn't found"));
    }
}
