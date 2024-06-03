package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PermissionService {

    PaginationResponse<Permission> index(Sort.Direction direction, int page, int perPage);

    List<Permission> getAllPermission();

    Permission store(aminurdev.com.backend.domain.request.Permission permission);

    Permission edit(Integer permissionId);

    Permission update(aminurdev.com.backend.domain.request.Permission permission, Integer permissionId);

    void delete(Integer permissionId);
}
