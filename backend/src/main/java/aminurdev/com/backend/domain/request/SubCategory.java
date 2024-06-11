package aminurdev.com.backend.domain.request;

import aminurdev.com.backend.domain.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategory {

    private Integer id;

    private Integer category_id;

    @NotBlank(message = "Name must not be blank")
    @NotNull(message = "Name field is required")
    @Size(min = 1, max = 125, message = "Name must be between 1 and 125 characters")
    private String name;

    private String description;

    private boolean status;
}
