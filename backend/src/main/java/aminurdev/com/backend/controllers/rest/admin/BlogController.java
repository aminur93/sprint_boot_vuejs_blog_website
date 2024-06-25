package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.BlogService;
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

@CrossOrigin(origins = "*", maxAge = 6000)
@RestController
@RequestMapping("/api/v1/admin/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping
    @PreAuthorize("hasAuthority('blog-list')")
    public ResponseEntity<PaginationResponse<Blog>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    )
    {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Blog> paginationResponse = blogService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/all-blogs")
    @PreAuthorize("hasAuthority('blog-list')")
    public ResponseEntity<ResponseWrapper> getAllBlogs()
    {
        List<Blog> blogs = blogService.getAllBlogs();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                blogs,
                "Blogs fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('blog-create')")
    public ResponseEntity<ResponseWrapper> create(@Valid @ModelAttribute aminurdev.com.backend.domain.request.Blog blogRequest)
    {
        try {
            Blog blog = blogService.store(blogRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(blog),
                    "Blog create successful",
                    "true",
                    HttpStatus.CREATED.value()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);

        } catch (Exception exception) {

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
    @PreAuthorize("hasAuthority('blog-edit')")
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer blogId)
    {
        try {
            Blog blog = blogService.edit(blogId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    blog,
                    "Blog fetch successful",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (RequestRejectedException exception) {

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record Not Found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (Exception exception) {

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseWrapper);
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('blog-edit')")
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer blogId, @Valid @ModelAttribute aminurdev.com.backend.domain.request.Blog blogRequest){

        try {
            Blog blog = blogService.update(blogId, blogRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(blog),
                    "Blog update successful",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (RequestRejectedException exception) {

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record Not Found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (Exception exception) {

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseWrapper);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('blog-delete')")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer blogId)
    {
        try {
            blogService.destroy(blogId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(""),
                    "Blog delete successful",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (RequestRejectedException exception) {

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(exception.getMessage()),
                    "Record Not Found",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (Exception exception) {

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Failed",
                    "false",
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseWrapper);
        }
    }
}

