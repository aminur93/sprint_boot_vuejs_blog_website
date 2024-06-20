package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.MenuDropdown;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface MenuDropdownService {

    PaginationResponse<MenuDropdown> index(Sort.Direction direction, int page, int perPage);

    List<MenuDropdown> getAllDropdowns();

    MenuDropdown store(aminurdev.com.backend.domain.request.MenuDropdown menuDropdownRequest);

    MenuDropdown edit(Integer menuDropdownId);

    MenuDropdown update(Integer menuDropdownId, aminurdev.com.backend.domain.request.MenuDropdown menuDropdownRequest);

    void destroy(Integer menuDropdownId);
}
