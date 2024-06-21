package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Menu;
import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface MenuService {

    PaginationResponse<Menu> index(Sort.Direction direction, int page, int perPage);

    List<Menu> getAllMenu();


    List<Map<String, Object>> getMenusWithPermissionsAndDropdowns(List<Permission> permissions);


    Menu store(aminurdev.com.backend.domain.request.Menu menuRequest);

    Menu edit(Integer menuId);

    Menu update(Integer menuId, aminurdev.com.backend.domain.request.Menu menuRequest);

    void destroy(Integer menuId);
}
