package aminurdev.com.backend.service;

import aminurdev.com.backend.response.AuthResponse;

public interface LoginService {

    AuthResponse login(aminurdev.com.backend.domain.request.User userRequest);

    AuthResponse refreshToken(aminurdev.com.backend.domain.request.Token tokenRequest);
}
