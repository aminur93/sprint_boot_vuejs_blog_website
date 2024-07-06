package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Comment;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CommentService {

    PaginationResponse<Comment> index(Sort.Direction direction, int page, int perPage);

    List<Comment> getAllComments();

    Comment store(aminurdev.com.backend.domain.request.Comment commentRequest);

    Comment edit(Integer commentId);

    Comment update(Integer commentId, aminurdev.com.backend.domain.request.Comment commentRequest);

    void delete(Integer commentId);
}
