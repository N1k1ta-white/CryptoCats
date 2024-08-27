package cryptocats.backend.entity;

import cryptocats.backend.entity.embedded.OwnershipId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ownership")
public class Ownership {
    @EmbeddedId
    private OwnershipId ownershipId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("catId")
    @JoinColumn(name = "cat_id")
    private Cat cat;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private Long amount;
}
