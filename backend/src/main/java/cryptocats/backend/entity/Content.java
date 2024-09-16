package cryptocats.backend.entity;

import cryptocats.backend.entity.embedded.ContentId;
import cryptocats.backend.entity.interfaces.Searchable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@Table(name = "content")
public class Content implements Searchable {
    @EmbeddedId
    private ContentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eggId")
    @JoinColumn(name = "egg_id")
    private Egg egg;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("catId")
    @JoinColumn(name = "cat_id")
    private Cat cat;

    private Long chance;

    @Transient
    private long conclusiveProbability;

    @Override
    public long getMetric() {
        return conclusiveProbability;
    }
}
