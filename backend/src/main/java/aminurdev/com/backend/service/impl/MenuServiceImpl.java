package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.Menu;
import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.MenuRepository;
import aminurdev.com.backend.domain.repository.PermissionRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.MenuService;
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
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public PaginationResponse<Menu> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

        Page<Menu> menuPage = menuRepository.findAll(pageable);

        List<Menu> menus = menuPage.getContent();

        PaginationResponse<Menu> response = new PaginationResponse<>();

        response.setData(menus);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All menu fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(menuPage.getNumber() + 1);
        meta.setFrom(menuPage.getNumber() * menuPage.getSize() + 1);
        meta.setLastPage(menuPage.getTotalPages());
        meta.setPath("http://localhost:8080/api/v1/admin" + "/menu");
        meta.setPerPage(menuPage.getSize());
        meta.setTo((int) menuPage.getTotalElements());
        meta.setTotal((int) menuPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("http://localhost:8080/api/v1/admin" + "/menu?page=1");
        links.setLast("http://localhost:8080/api/v1/admin" + "/menu?page=" + menuPage.getTotalPages());
        if (menuPage.hasPrevious()) {
            links.setPrev("http://localhost:8080/api/v1/admin" + "/menu?page=" + menuPage.previousPageable().getPageNumber());
        }
        if (menuPage.hasNext()) {
            links.setNext("http://localhost:8080/api/v1/admin" + "/menu?page=" + menuPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<Menu> getAllMenu() {

        List<Menu> menus = menuRepository.findAll();

        return menus;
    }

    @Override
    public Menu store(aminurdev.com.backend.domain.request.Menu menuRequest) {

        try{

            Menu menu = new Menu();

            if (menuRequest.getPermission_id() != null)
            {
                Permission permission = permissionRepository.findById(menuRequest.getPermission_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Permission not found: " + menuRequest.getPermission_id()));

                menu.setPermission(permission);
            }

            menu.setTitle(menuRequest.getTitle());
            menu.setIcon(menuRequest.getIcon());
            menu.setRoute(menuRequest.getRoute());
            menu.setHeaderMenu(menuRequest.isHeader_menu());
            menu.setSidebarMenu(menuRequest.isSidebar_menu());
            menu.setDropdown(menuRequest.isDropdown());

            return menuRepository.save(menu);

        }catch (Exception exception){

            throw new CustomException("Error while creating menu: " + exception.getMessage(), exception);
        }
    }

    @Override
    public Menu edit(Integer menuId) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new ResourceNotFoundException("Menu not found: " + menuId));

        if (menu == null) {
            throw new ResourceNotFoundException("Menu not found: " + menuId);
        }

        return menu;
    }

    @Override
    public Menu update(Integer menuId, aminurdev.com.backend.domain.request.Menu menuRequest) {

        try{

            Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new ResourceNotFoundException("Menu not found: " + menuId));

            if (menuRequest.getPermission_id() != null)
            {
                Permission permission = permissionRepository.findById(menuRequest.getPermission_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Permission not found: " + menuRequest.getPermission_id()));

                menu.setPermission(permission);
            }

            menu.setTitle(menuRequest.getTitle());
            menu.setIcon(menuRequest.getIcon());
            menu.setRoute(menuRequest.getRoute());
            menu.setHeaderMenu(menuRequest.isHeader_menu());
            menu.setSidebarMenu(menuRequest.isSidebar_menu());
            menu.setDropdown(menuRequest.isDropdown());

            return menuRepository.save(menu);

        }catch (Exception exception){

            throw new CustomException("Error while updating menu: " + exception.getMessage(), exception);
        }

    }

    @Override
    public void destroy(Integer menuId) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new ResourceNotFoundException("Menu not found: " + menuId));

        if (menu == null) {
            throw new ResourceNotFoundException("Menu not found: " + menuId);
        }

        menuRepository.delete(menu);
    }
}
