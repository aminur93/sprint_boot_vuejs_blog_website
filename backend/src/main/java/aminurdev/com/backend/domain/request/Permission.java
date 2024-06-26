package aminurdev.com.backend.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Permission {

    private Integer id;

    @NotBlank(message = "Name must not be blank")
    @NotNull(message= "Name field is required")
    private String name;
}
