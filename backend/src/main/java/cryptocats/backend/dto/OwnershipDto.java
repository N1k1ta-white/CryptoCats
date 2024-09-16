package cryptocats.backend.dto;

import cryptocats.backend.entity.Cat;
import cryptocats.backend.entity.User;
import cryptocats.backend.entity.embedded.OwnershipId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnershipDto {
    private OwnershipId ownershipId;

    private Cat cat;
    private User user;
    private Long amount;
    private Instant firstReceivedAt;
}
