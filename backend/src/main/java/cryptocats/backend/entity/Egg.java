package cryptocats.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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

    @OneToMany(mappedBy = "egg")
    Set<Content> contentSet;
}
