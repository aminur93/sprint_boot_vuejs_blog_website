package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.domain.entity.Role;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<PaginationResponse<Role>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Role> paginationResponse = roleService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

//    @GetMapping
//    public ResponseEntity<ResponseWrapper> getAllRole(){
//
//        List<Role> roles = roleService.getAllRole();
//
//        ResponseWrapper responseWrapper = new ResponseWrapper().success(
//                Collections.singletonList(roles),
//                "All Role fetch successful",
//                "true",
//                HttpStatus.OK.value()
//        );
//
//        return ResponseEntity.ok(responseWrapper);
//    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> store(@Valid @RequestBody aminurdev.com.backend.domain.request.Role roleRequest){

        try{

            Role role = roleService.store(roleRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(role),
                    "Role store successful",
                    "true",
                    HttpStatus.CREATED.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (Exception exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseWrapper);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer roleId){

        try{

            Role role = roleService.edit(roleId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(role),
                    "Role fetch successful",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (RequestRejectedException exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record Not Found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (Exception exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Failed, server error",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer roleId, @Valid @RequestBody aminurdev.com.backend.domain.request.Role roleRequest){

        try{

            Role role = roleService.update(roleRequest, roleId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(role),
                    "Role update successful",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (RequestRejectedException exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record not found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (Exception exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Failed, server error",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer roleId){
        try{

            roleService.delete(roleId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(""),
                    "Role delete successful",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (RequestRejectedException exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record not found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (Exception exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Failed, server error",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);
        }
    }
}
