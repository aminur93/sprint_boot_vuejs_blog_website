package aminurdev.com.backend.controllers.rest.auth;

import aminurdev.com.backend.domain.request.Token;
import aminurdev.com.backend.response.AuthResponse;
import aminurdev.com.backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 60000)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody aminurdev.com.backend.domain.request.User userRequest)
    {
        return ResponseEntity.ok(loginService.login(userRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Token tokenRequest)
    {
        return ResponseEntity.ok(loginService.refreshToken(tokenRequest));
    }
}
