package cryptocats.backend.entity;

import cryptocats.backend.entity.enums.EggStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "egg")
public class Egg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String img;
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private EggStatus status;

    @OneToMany(mappedBy = "egg", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Content> contentList;

    @Transient
    private boolean isComputedProbability;

    public boolean isGift() {
        return status == EggStatus.GIFT;
    }

    public void computeProbability() {
        long conclusiveProbability = 0;
        for (Content content : contentList) {
            conclusiveProbability += content.getChance();
            content.setChance(conclusiveProbability);
        }

        isComputedProbability = true;
    }
}
