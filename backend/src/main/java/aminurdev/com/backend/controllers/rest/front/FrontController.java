package aminurdev.com.backend.controllers.rest.front;

import aminurdev.com.backend.domain.entity.*;
import aminurdev.com.backend.domain.request.NewsLetter;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.FrontService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 6000)
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

    @GetMapping("/api/v1/public/category")
    public ResponseEntity<ResponseWrapper> getAllCategories()
    {
        List<Category> categories = frontService.getAllCategories();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                categories,
                "All categories fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @GetMapping("/api/v1/public/single-category/{id}")
    public ResponseEntity<ResponseWrapper> getCategory(@PathVariable("id") Integer categoryId)
    {
        Category category = frontService.getCategory(categoryId);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                category,
                "Category fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }



    @GetMapping("/api/v1/public/sub-category")
    public ResponseEntity<ResponseWrapper> getAllSubCategories()
    {
        List<SubCategory> subCategories = frontService.getAllSubCategories();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                subCategories,
                "All sub categories fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @GetMapping("/api/v1/public/sub-category/{id}")
    public ResponseEntity<ResponseWrapper> getSubCategory(@PathVariable("id") Integer subCategoryId) {
        SubCategory subCategory = frontService.getSubCategory(subCategoryId);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                subCategory,
                "Sub category fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @GetMapping("/api/v1/public/tag")
    public ResponseEntity<ResponseWrapper> getAllTags()
    {
        List<Tag> tags = frontService.getAllTags();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                tags,
                "All tags fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @GetMapping("/api/v1/public/tag/{id}")
    public ResponseEntity<ResponseWrapper> getTag(@PathVariable("id") Integer tagId)
    {
        Tag tag = frontService.getTag(tagId);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                tag,
                "Tag fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @PostMapping("/api/v1/public/news-letter")
    public ResponseEntity<ResponseWrapper> storeNewsLetter(@Valid @RequestBody NewsLetter newsLetterRequest)
    {
        try{

            aminurdev.com.backend.domain.entity.NewsLetter newsLetter = frontService.StoreNewsLetter(newsLetterRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(newsLetter),
                    "News letter created successfully",
                    "true",
                    HttpStatus.CREATED.value()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);

        }catch (Exception exception){
            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while creating news letter",
                    "false",
                    HttpStatus.BAD_REQUEST.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);
        }
    }

    @GetMapping("/api/v1/public/category-blog/{categoryId}")
    public ResponseEntity<PaginationResponse<Blog>> getCategoryBlogs(
            @PathVariable("categoryId") Category categoryId,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    )
    {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Blog> paginationResponse = frontService.getCategoryBlogs(categoryId, direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/api/v1/public/sub-category-blog/{subCategoryId}")
    public ResponseEntity<PaginationResponse<Blog>> getSubCategoryBlogs(
            @PathVariable("subCategoryId") SubCategory subCategoryId,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    )
    {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Blog> paginationResponse = frontService.getSubCategoryBlogs(subCategoryId, direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/api/v1/public/tag-blog/{tagId}")
    public ResponseEntity<PaginationResponse<Blog>> getBlogsByTag(
            @PathVariable("tagId") Tag tagId,
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    )
    {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Blog> paginationResponse = frontService.getBlogsByTag(tagId, direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @PostMapping("/api/v1/public/contact-us")
    public ResponseEntity<ResponseWrapper> storeContactUs(@Valid @RequestBody aminurdev.com.backend.domain.request.ContactUs contactUsRequest)
    {
        try{

            ContactUs contactUs = frontService.storeContactUs(contactUsRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(contactUs),
                    "Contact us created successfully",
                    "true",
                    HttpStatus.CREATED.value()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(responseWrapper);

        }catch (Exception exception){
            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while creating news letter",
                    "false",
                    HttpStatus.BAD_REQUEST.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);
        }
    }
}
