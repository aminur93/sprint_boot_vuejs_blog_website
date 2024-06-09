package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Role;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface RoleService {

    PaginationResponse<Role> index(Sort.Direction direction, int page, int perPage);

    List<Role> getAllRole();

    Role store(aminurdev.com.backend.domain.request.Role roleRequest);

    Role edit(Integer roleId);

    Role update(aminurdev.com.backend.domain.request.Role roleRequest, Integer roleId);

    void delete(Integer roleId);
}
