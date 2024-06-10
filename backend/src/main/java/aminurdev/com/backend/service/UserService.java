package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.User;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {

    PaginationResponse<User> index(Sort.Direction direction, int page, int perPage);

    List<User> getAllUsers();

    User store(aminurdev.com.backend.domain.request.User userRequest);

    User edit(Integer userId);

    User update(Integer userId, aminurdev.com.backend.domain.request.User userRequest);

    void delete(Integer userId);
}
