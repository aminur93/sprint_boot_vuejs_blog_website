package aminurdev.com.backend.domain.request;

import aminurdev.com.backend.domain.entity.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class User {

    private Integer id;

    @NotBlank(message = "Name must not be blank")
    @NotNull(message = "Name field is required")
    @Size(min = 1, max = 125, message = "Name must be between 1 and 125 characters")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @NotNull(message = "Email field is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
            message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character (@#$%^&+=)"
    )
    private String password;

    @NotNull(message = "Role cannot be null")
    private Integer role;
}
