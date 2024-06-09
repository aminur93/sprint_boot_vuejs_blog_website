package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.User;

public interface RegisterService {

    User register(aminurdev.com.backend.domain.request.User user);
}
