package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Token;
import aminurdev.com.backend.domain.entity.TokenType;
import aminurdev.com.backend.domain.entity.User;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.TokenRepository;
import aminurdev.com.backend.domain.repository.UserRepository;
import aminurdev.com.backend.response.AuthResponse;
import aminurdev.com.backend.service.JwtService;
import aminurdev.com.backend.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final TokenRepository tokenRepository;


    @Override
    public AuthResponse login(aminurdev.com.backend.domain.request.User userRequest) {

        AuthResponse response = new AuthResponse();

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getEmail(),
                            userRequest.getPassword()
                    )
            );

            User user = userRepository.findByEmail(userRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

            String jwtToken = jwtService.generateToken(user);

            revokedAllUserTokens(user);

            saveUserToken(user, jwtToken);

            String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

            if (user.getId() > 0)
            {
                User responseData = new User();

                responseData.setName(user.getName());
                responseData.setEmail(user.getEmail());
                //responseData.setRole(user.getRole());
                responseData.setCreatedAt( user.getCreatedAt());
                responseData.setUpdatedAt(user.getUpdatedAt());

                response.setUser(responseData);
                response.setMessage("Login successful");
                response.setStatusCode(HttpStatus.OK.value());
                response.setToken(jwtToken);
                response.setRefreshToken(refreshToken);
                response.setExpiration("24Hr");
            }

        }catch (Exception exception){

            throw new CustomException("Error while login user: " + exception.getMessage(), exception);
        }

        return response;
    }

    @Override
    public AuthResponse refreshToken(aminurdev.com.backend.domain.request.Token tokenRequest) {

        AuthResponse response = new AuthResponse();

        try{

            String userEmail = jwtService.extractUserName(tokenRequest.getRefreshToken());

            User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

            if (jwtService.isValidToken(tokenRequest.getRefreshToken(), user)) {

                var jwt = jwtService.generateToken(user);

                revokedAllUserTokens(user);

                saveUserToken(user, jwt);

                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(tokenRequest.getRefreshToken());
                response.setExpiration("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }

        }catch (Exception e){

            response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            response.setMessage(e.getMessage());
        }

        return  response;
    }


    private void revokedAllUserTokens(User user)
    {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());

        if ((validUserTokens.isEmpty()))
        {
            return;
        }

        validUserTokens.forEach(t -> {
            t.setExpired(1);
            t.setRevoked(1);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public void saveUserToken(User user, String jwtToken)
    {
        Token token = new Token();

        token.setUser(user);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(0);
        token.setRevoked(0);

        tokenRepository.save(token);
    }
}
