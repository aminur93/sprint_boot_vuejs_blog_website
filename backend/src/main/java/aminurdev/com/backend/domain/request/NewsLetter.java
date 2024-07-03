package aminurdev.com.backend.domain.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsLetter {

    private Integer id;

    @NotNull(message = "Email is required")
    private String email;
}
