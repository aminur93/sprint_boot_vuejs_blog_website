package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.domain.entity.Menu;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    @PreAuthorize("hasAuthority('menu-list')")
    public ResponseEntity<PaginationResponse<Menu>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    )
    {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Menu> paginationResponse = menuService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/all-menus")
    @PreAuthorize("hasAuthority('menu-list')")
    public ResponseEntity<ResponseWrapper> getAllMenus()
    {
        List<Menu> menus = menuService.getAllMenu();

        return ResponseEntity.ok(new ResponseWrapper().success(
                menus,
                "Menu fetch successful",
                "true",
                HttpStatus.OK.value()
        ));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('menu-create')")
    public ResponseEntity<ResponseWrapper> store(@RequestBody aminurdev.com.backend.domain.request.Menu menuRequest)
    {
        try{

            Menu menu = menuService.store(menuRequest);

            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(menu),
                    "Menu store successful",
                    "true",
                    HttpStatus.CREATED.value()
            ));
        }catch (Exception exception){
            return ResponseEntity.ok(new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            ));
        }
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('menu-edit')")
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer menuId)
    {
        try{

            Menu menu = menuService.edit(menuId);

            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(menu),
                    "Menu fetch successful",
                    "true",
                    HttpStatus.OK.value()
            ));
        }catch (RequestRejectedException exception){
            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record Not Found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            ));
        }catch (Exception exception){
            return ResponseEntity.ok(new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            ));
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('menu-edit')")
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer menuId, @RequestBody aminurdev.com.backend.domain.request.Menu menuRequest)
    {
        try{

            Menu menu = menuService.update(menuId, menuRequest);

            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(menu),
                    "Menu update successful",
                    "true",
                    HttpStatus.OK.value()
            ));
        }catch (RequestRejectedException exception){
            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record Not Found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            ));
        }catch (Exception exception){
            return ResponseEntity.ok(new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            ));
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('menu-delete')")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer menuId)
    {
        try{

            menuService.destroy(menuId);

            return ResponseEntity.ok(new ResponseWrapper().success(
                    null,
                    "Menu delete successful",
                    "true",
                    HttpStatus.OK.value()
            ));
        }catch (RequestRejectedException exception){
            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record Not Found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            ));
        }catch (Exception exception){
            return ResponseEntity.ok(new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            ));
        }
    }
}
