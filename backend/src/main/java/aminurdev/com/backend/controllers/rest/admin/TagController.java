package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.domain.entity.Tag;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    @PreAuthorize("hasAuthority('tag-list')")
    public ResponseEntity<PaginationResponse<Tag>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Tag> paginationResponse = tagService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/all-tags")
    @PreAuthorize("hasAuthority('tag-list')")
    public ResponseEntity<ResponseWrapper> getAllTag()
    {
        List<Tag> tags = tagService.getAllTag();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                Collections.singletonList(tags),
                "All tags fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('tag-create')")
    public ResponseEntity<ResponseWrapper> store(@Valid @RequestBody aminurdev.com.backend.domain.request.Tag tagRequest)
    {
        try {

            Tag createdTag = tagService.store(tagRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(createdTag),
                    "Tag created successfully",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        }catch (Exception exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while creating tag",
                    "false",
                    HttpStatus.BAD_REQUEST.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);
        }
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('tag-edit')")
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer tagId){

        try{

            Tag tag = tagService.edit(tagId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(tag),
                    "Tag fetch successful",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (ResourceNotFoundException exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while fetching tag",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);
        } catch (Exception exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while fetching tag",
                    "false",
                    HttpStatus.BAD_REQUEST.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);
        }
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('tag-update')")
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer tagId, @Valid @RequestBody aminurdev.com.backend.domain.request.Tag tagRequest){

        try{

            Tag updatedTag = tagService.update(tagId, tagRequest);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    Collections.singletonList(updatedTag),
                    "Tag updated successfully",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (ResourceNotFoundException exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while updating tag",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);
        } catch (Exception exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while updating tag",
                    "false",
                    HttpStatus.BAD_REQUEST.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('tag-delete')")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer tagId){

        try{

            tagService.delete(tagId);

            ResponseWrapper responseWrapper = new ResponseWrapper().success(
                    null,
                    "Tag deleted successfully",
                    "true",
                    HttpStatus.OK.value()
            );

            return ResponseEntity.ok(responseWrapper);

        } catch (ResourceNotFoundException exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while deleting tag",
                    "false",
                    HttpStatus.NOT_FOUND.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);

        } catch (Exception exception){

            ResponseWrapper responseWrapper = new ResponseWrapper().error(
                    Collections.singletonList(exception.getMessage()),
                    "Error while deleting tag",
                    "false",
                    HttpStatus.BAD_REQUEST.value()
            );

            return ResponseEntity.badRequest().body(responseWrapper);
        }
    }
}
