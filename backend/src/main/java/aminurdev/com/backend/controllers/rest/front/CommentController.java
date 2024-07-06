package aminurdev.com.backend.controllers.rest.front;

import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.Comment;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/v1/admin/comment")
    @PreAuthorize("hasAuthority('comment-list')")
    public ResponseEntity<PaginationResponse<Comment>> index(
            @RequestParam(defaultValue = "DESC") String sortDirection,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());

        PaginationResponse<Comment> paginationResponse = commentService.index(direction, page, perPage);

        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/api/v1/public/all-comment")
    public ResponseEntity<ResponseWrapper> getAllComments(){

        List<Comment> comments = commentService.getAllComments();

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                comments,
                "Comment fetch successful",
                "true",
                200
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @PostMapping("/api/v1/public/comment")
    public ResponseEntity<ResponseWrapper> store(@Valid @RequestBody aminurdev.com.backend.domain.request.Comment commentRequest){

        Comment comment = commentService.store(commentRequest);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                Collections.singletonList(comment),
                "Comment store successful",
                "true",
                HttpStatus.CREATED.value()
        );

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(responseWrapper);
    }

    @GetMapping("/api/v1/admin/comment/{id}")
    @PreAuthorize("hasAuthority('comment-edit')")
    public ResponseEntity<ResponseWrapper> edit(@PathVariable("id") Integer commentId){

        Comment comment = commentService.edit(commentId);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                comment,
                "Comment fetch successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @PutMapping("/api/v1/admin/comment/{id}")
    @PreAuthorize("hasAuthority('comment-edit')")
    public ResponseEntity<ResponseWrapper> update(@PathVariable("id") Integer commentId, @Valid @RequestBody aminurdev.com.backend.domain.request.Comment commentRequest){

        Comment comment = commentService.update(commentId, commentRequest);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                Collections.singletonList(comment),
                "Comment update successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }

    @DeleteMapping("/api/v1/admin/comment/{id}")
    @PreAuthorize("hasAuthority('comment-delete')")
    public ResponseEntity<ResponseWrapper> delete(@PathVariable("id") Integer commentId){

        commentService.delete(commentId);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                Collections.emptyList(),
                "Comment delete successful",
                "true",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }
}
