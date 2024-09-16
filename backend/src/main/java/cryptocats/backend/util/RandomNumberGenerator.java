package cryptocats.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator {

    @Value("${probability.limit}")
    private Long limit;

    public Long generate() {
        return (long)(Math.random() * limit);
    }
}
