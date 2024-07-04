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
public class ContactUs {

    private Integer id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Message cannot be null")
    private String message;
}
