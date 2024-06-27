package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Menu;
import aminurdev.com.backend.domain.entity.MenuDropdown;
import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.MenuDropdownRepository;
import aminurdev.com.backend.domain.repository.MenuRepository;
import aminurdev.com.backend.domain.repository.PermissionRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.MenuDropdownService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuDropdownServiceImpl implements MenuDropdownService {

    private final MenuDropdownRepository menuDropdownRepository;

    private final MenuRepository menuRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public PaginationResponse<MenuDropdown> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

        Page<MenuDropdown> menuDropdownPage = menuDropdownRepository.findAll(pageable);

        List<MenuDropdown> menuDropdowns = menuDropdownPage.getContent();

        PaginationResponse<MenuDropdown> response = new PaginationResponse<>();

        response.setData(menuDropdowns);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All menu dropdown fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(menuDropdownPage.getNumber() + 1);
        meta.setFrom(menuDropdownPage.getNumber() * menuDropdownPage.getSize() + 1);
        meta.setLastPage(menuDropdownPage.getTotalPages());
        meta.setPath("http://localhost:8080/api/v1/admin" + "/menu-dropdown");
        meta.setPerPage(menuDropdownPage.getSize());
        meta.setTo((int) menuDropdownPage.getTotalElements());
        meta.setTotal((int) menuDropdownPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("http://localhost:8080/api/v1/admin" + "/menu-dropdown?page=1");
        links.setLast("http://localhost:8080/api/v1/admin" + "/menu-dropdown?page=" + menuDropdownPage.getTotalPages());
        if (menuDropdownPage.hasPrevious()) {
            links.setPrev("http://localhost:8080/api/v1/admin" + "/menu-dropdown?page=" + menuDropdownPage.previousPageable().getPageNumber());
        }
        if (menuDropdownPage.hasNext()) {
            links.setNext("http://localhost:8080/api/v1/admin" + "/menu-dropdown?page=" + menuDropdownPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<MenuDropdown> getAllDropdowns() {
        return menuDropdownRepository.findAll();
    }

    @Override
    public MenuDropdown store(aminurdev.com.backend.domain.request.MenuDropdown menuDropdownRequest) {

        try {

            MenuDropdown menuDropdown = new MenuDropdown();

            if (menuDropdownRequest.getMenu_id() != null)
            {
                Menu menu = menuRepository.findById(menuDropdownRequest.getMenu_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Menu not found: " + menuDropdownRequest.getMenu_id()));

                menuDropdown.setMenu(menu);
            }

            if (menuDropdownRequest.getPermission_id() != null)
            {
                Permission permission = permissionRepository.findById(menuDropdownRequest.getPermission_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Permission not found: " + menuDropdownRequest.getPermission_id()));

                menuDropdown.setPermission(permission);
            }

            menuDropdown.setTitle(menuDropdownRequest.getTitle());
            menuDropdown.setIcon(menuDropdownRequest.getIcon());
            menuDropdown.setRoute(menuDropdownRequest.getRoute());

            return menuDropdownRepository.save(menuDropdown);

        }catch (Exception exception){

            throw new CustomException("Error while creating menu dropdown: " + exception.getMessage(), exception);
        }
    }

    @Override
    public MenuDropdown edit(Integer menuDropdownId) {

        MenuDropdown menuDropdown = menuDropdownRepository.findById(menuDropdownId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu dropdown not found: " + menuDropdownId));

        if (menuDropdown == null) {
            throw new ResourceNotFoundException("Menu dropdown not found: " + menuDropdownId);
        }

        return menuDropdown;
    }

    @Override
    public MenuDropdown update(Integer menuDropdownId, aminurdev.com.backend.domain.request.MenuDropdown menuDropdownRequest) {

        try {

            MenuDropdown menuDropdown = menuDropdownRepository.findById(menuDropdownId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu dropdown not found: " + menuDropdownId));

            if (menuDropdown == null) {
                throw new ResourceNotFoundException("Menu dropdown not found: " + menuDropdownId);
            }

            if (menuDropdownRequest.getMenu_id() != null)
            {
                Menu menu = menuRepository.findById(menuDropdownRequest.getMenu_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Menu not found: " + menuDropdownRequest.getMenu_id()));

                menuDropdown.setMenu(menu);
            }

            if (menuDropdownRequest.getPermission_id() != null)
            {
                Permission permission = permissionRepository.findById(menuDropdownRequest.getPermission_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Permission not found: " + menuDropdownRequest.getPermission_id()));

                menuDropdown.setPermission(permission);
            }

            menuDropdown.setTitle(menuDropdownRequest.getTitle());
            menuDropdown.setIcon(menuDropdownRequest.getIcon());
            menuDropdown.setRoute(menuDropdownRequest.getRoute());

            return menuDropdownRepository.save(menuDropdown);

        }catch (Exception exception){

            throw new CustomException("Error while updating menu dropdown: " + exception.getMessage(), exception);
        }

    }

    @Override
    public void destroy(Integer menuDropdownId) {

        MenuDropdown menuDropdown = menuDropdownRepository.findById(menuDropdownId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu dropdown not found: " + menuDropdownId));

        if (menuDropdown == null) {
            throw new ResourceNotFoundException("Menu dropdown not found: " + menuDropdownId);
        }

        menuDropdownRepository.delete(menuDropdown);

    }
}
