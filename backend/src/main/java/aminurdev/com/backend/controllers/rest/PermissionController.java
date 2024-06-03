package aminurdev.com.backend.controllers.rest;

import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin/permission")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<PaginationResponse<Permission>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    )
    {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Permission> paginationResponse = permissionService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/all-permission")
    public ResponseEntity<ResponseWrapper> allPermission()
    {
        List<Permission> permissions = permissionService.getAllPermission();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                Collections.singletonList(permissions),
                "All Permission fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);

    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> store(@Valid @RequestBody aminurdev.com.backend.domain.request.Permission permissionRequest)
    {
        try{

            Permission permission = permissionService.store(permissionRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(permission),
                    "store successful",
                    "true",
                    HttpStatus.CREATED.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (Exception e){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(e.getMessage()),
                    "failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseWrapper);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer permissionId)
    {
        try{

            Permission permission = permissionService.edit(permissionId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(permission),
                    "Permission fetch successful",
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
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer permissionId, @Valid @RequestBody aminurdev.com.backend.domain.request.Permission permissionRequest)
    {
        try{

            Permission permission = permissionService.update(permissionRequest, permissionId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(permission),
                    "Permission update successful",
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
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer permissionId)
    {
        try{

            permissionService.delete(permissionId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(""),
                    "Permission delete successful",
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
