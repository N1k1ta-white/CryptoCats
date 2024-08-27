package cryptocats.backend.repository;

import cryptocats.backend.entity.Egg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EggRepository extends JpaRepository<Egg, Long> {
}
