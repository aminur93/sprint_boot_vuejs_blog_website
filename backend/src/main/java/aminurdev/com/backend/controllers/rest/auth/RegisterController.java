package aminurdev.com.backend.controllers.rest.auth;

import aminurdev.com.backend.domain.entity.User;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper> register(@Valid @RequestBody aminurdev.com.backend.domain.request.User userRequest)
    {
        try{

            User user = registerService.register(userRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(user),
                    "Register successful",
                    "true",
                    HttpStatus.CREATED.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (Exception e){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(e.getMessage()),
                    "failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseWrapper);
        }
    }
}
