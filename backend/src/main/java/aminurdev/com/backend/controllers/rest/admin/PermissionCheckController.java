package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 60000)
@RestController
@RequestMapping("/api/v1/admin/check-permission")
@RequiredArgsConstructor
public class PermissionCheckController {

    @GetMapping
    public ResponseEntity<String> checkPermission(@RequestParam String permission) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userDetails != null && hasPermission(userDetails, permission)) {
            return ResponseEntity.ok("{\"message\": \"Permission granted\"}");
        }

        return ResponseEntity.status(403).body("{\"message\": \"Forbidden\"}");
    }

    private boolean hasPermission(UserDetails userDetails, String permission) {
         return userDetails.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(permission));
    }
}
