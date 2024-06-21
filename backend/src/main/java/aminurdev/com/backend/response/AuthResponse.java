package aminurdev.com.backend.response;

import aminurdev.com.backend.domain.entity.Menu;
import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.domain.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expiration;
    private aminurdev.com.backend.domain.entity.User User;
    private Role role;
    private List<Permission> permissions;
    private List<Map<String, Object>> menus;
}
