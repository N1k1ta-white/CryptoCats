package cryptocats.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CatDto {
    private Long id;
    @NotNull
    private Long cost;
    @NotNull
    private String name;
    @NotNull
    private String img;
}
