package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.domain.entity.Menu;
import aminurdev.com.backend.domain.entity.MenuDropdown;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.MenuDropdownService;
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
@RequestMapping("/api/v1/admin/menu-dropdown")
@RequiredArgsConstructor
public class MenuDropdownController {

    private final MenuDropdownService menuDropdownService;

    @GetMapping
    @PreAuthorize("hasAuthority('menuDropdown-list')")
    public ResponseEntity<PaginationResponse<MenuDropdown>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<MenuDropdown> paginationResponse = menuDropdownService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/all-menu-dropdown")
    @PreAuthorize("hasAuthority('menuDropdown-list')")
    public ResponseEntity<ResponseWrapper> getAllMenuDropdown(){

        List<MenuDropdown> menuDropdowns = menuDropdownService.getAllDropdowns();

        return ResponseEntity.ok(new ResponseWrapper().success(
                Collections.singletonList(menuDropdowns),
                "Menu dropdown fetch successful",
                "true",
                HttpStatus.OK.value()
        ));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('menuDropdown-create')")
    public ResponseEntity<ResponseWrapper> store(@RequestBody aminurdev.com.backend.domain.request.MenuDropdown menuDropdownRequest){

        try{

            MenuDropdown menuDropdown = menuDropdownService.store(menuDropdownRequest);

            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(menuDropdown),
                    "Menu dropdown store successful",
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
    @PreAuthorize("hasAuthority('menuDropdown-edit')")
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer menuDropdownId){

        try{

            MenuDropdown menuDropdown = menuDropdownService.edit(menuDropdownId);

            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(menuDropdown),
                    "Menu dropdown fetch successful",
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
    @PreAuthorize("hasAuthority('menuDropdown-edit')")
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer menuDropdownId, @RequestBody aminurdev.com.backend.domain.request.MenuDropdown menuDropdownRequest){

        try{

            MenuDropdown menuDropdown = menuDropdownService.update(menuDropdownId, menuDropdownRequest);

            return ResponseEntity.ok(new ResponseWrapper().success(
                    Collections.singletonList(menuDropdown),
                    "Menu dropdown update successful",
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
    @PreAuthorize("hasAuthority('menuDropdown-delete')")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer menuDropdownId){

        try{

            menuDropdownService.destroy(menuDropdownId);

            return ResponseEntity.ok(new ResponseWrapper().success(
                    null,
                    "Menu dropdown delete successful",
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
