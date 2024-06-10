package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.CategoryService;
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
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<PaginationResponse<Category>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Category> paginationResponse = categoryService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/all-categories")
    public ResponseEntity<ResponseWrapper> getAllCategories(){

        List<Category> categories = categoryService.getAllCategories();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                Collections.singletonList(categories),
                "Category fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> store(@Valid @RequestBody aminurdev.com.backend.domain.request.Category categoryRequest){

        try{

            Category category = categoryService.store(categoryRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(category),
                    "Category store successful",
                    "true",
                    HttpStatus.CREATED.value()
            );

            return ResponseEntity.status(HttpStatus.CREATED.value()).body(responseWrapper);

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
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer categoryId){

        try{

            Category category = categoryService.edit(categoryId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(category),
                    "Category fetch successful",
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
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.ok(responseWrapper);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer categoryId, @Valid @RequestBody aminurdev.com.backend.domain.request.Category categoryRequest){

        try{

            Category category = categoryService.update(categoryId, categoryRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(category),
                    "Category update successful",
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
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.ok(responseWrapper);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer categoryId){

        try{

            categoryService.destroy(categoryId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(""),
                    "Category delete successful",
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
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.ok(responseWrapper);
        }
    }
}
