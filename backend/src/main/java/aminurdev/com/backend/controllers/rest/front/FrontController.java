package aminurdev.com.backend.controllers.rest.front;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.FrontService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FrontController {

    private final FrontService frontService;

    @GetMapping("/api/v1/public/blog")
    public ResponseEntity<PaginationResponse<Blog>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    )
    {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Blog> paginationResponse = frontService.getBlogs(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/api/v1/public/blog-details/{id}")
    public ResponseEntity<ResponseWrapper> blogDetails(@PathVariable("id") Integer blogId)
    {

        Blog blog = frontService.getBlogDetails(blogId);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                blog,
                "Blog details fetch successful",
                "true",
                HttpStatus.OK.value()
        );
        return ResponseEntity.ok(responseWrapper);
    }
}
