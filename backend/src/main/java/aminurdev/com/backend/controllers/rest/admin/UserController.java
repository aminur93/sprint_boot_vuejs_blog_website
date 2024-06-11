package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.domain.entity.Role;
import aminurdev.com.backend.domain.entity.User;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('user-list')")
    public ResponseEntity<PaginationResponse<User>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<User> paginationResponse = userService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/all-user")
    public ResponseEntity<ResponseWrapper> getAllUser() {

        List<User> users = userService.getAllUsers();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                Collections.singletonList(users),
                "User fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user-create')")
    public ResponseEntity<ResponseWrapper> store(@Valid @RequestBody aminurdev.com.backend.domain.request.User userRequest) {

        try{

            User user = userService.store(userRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(user),
                    "User store successful",
                    "true",
                    HttpStatus.CREATED.value()
            );
            return ResponseEntity.status(HttpStatus.CREATED.value()).body(responseWrapper);

        }catch (Exception exception) {
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
    @PreAuthorize("hasAuthority('user-edit')")
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer userId) {
        try{

            User user = userService.edit(userId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(user),
                    "User fetch successful",
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
    @PreAuthorize("hasAuthority('user-update')")
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer userId, @Valid @RequestBody aminurdev.com.backend.domain.request.User userRequest) {
        try{
            User user = userService.update(userId, userRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(user),
                    "User update successful",
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

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('user-delete')")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer userId) {
        try{

            userService.delete(userId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(""),
                    "User delete successful",
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
}
