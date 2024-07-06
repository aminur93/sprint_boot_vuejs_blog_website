package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.Comment;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.BlogRepository;
import aminurdev.com.backend.domain.repository.CommentRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BlogRepository blogRepository;

    @Override
    public PaginationResponse<Comment> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"id"));

        Page<Comment> commentPage = commentRepository.findAll(pageable);

        List<Comment> comments = commentPage.getContent();

        PaginationResponse<Comment> response = new PaginationResponse<>();

        response.setData(comments);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All comments fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(commentPage.getNumber() + 1);
        meta.setFrom(commentPage.getNumber() * commentPage.getSize() + 1);
        meta.setLastPage(commentPage.getTotalPages());
        meta.setPath("/category");
        meta.setPerPage(commentPage.getSize());
        meta.setTo((int) commentPage.getTotalElements());
        meta.setTotal((int) commentPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("/category?page=1");
        links.setLast("/category?page=" + commentPage.getTotalPages());
        if (commentPage.hasPrevious()) {
            links.setPrev("/category?page=" + commentPage.previousPageable().getPageNumber());
        }
        if (commentPage.hasNext()) {
            links.setNext("/category?page=" + commentPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment store(aminurdev.com.backend.domain.request.Comment commentRequest) {

        try {

            Comment comment = new Comment();

            Blog blog = blogRepository.findById(commentRequest.getBlog_id()).orElseThrow(() -> new ResourceNotFoundException("Blog is not found" + commentRequest.getBlog_id()));

            comment.setBlog(blog);
            comment.setName(commentRequest.getName());
            comment.setEmail(commentRequest.getEmail());
            comment.setComment(commentRequest.getComment());

            commentRepository.save(comment);

            return comment;

        }catch (Exception exception){

            throw new CustomException("Error while creating comment: " + exception.getMessage(), exception);
        }
    }

    @Override
    public Comment edit(Integer commentId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment is not found" + commentId));
        return comment;
    }

    @Override
    public Comment update(Integer commentId, aminurdev.com.backend.domain.request.Comment commentRequest) {

        try{
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment is not found" + commentId));

            Blog blog = blogRepository.findById(commentRequest.getBlog_id()).orElseThrow(() -> new ResourceNotFoundException("Blog is not found" + commentRequest.getBlog_id()));

            comment.setBlog(blog);
            comment.setName(commentRequest.getName());
            comment.setEmail(commentRequest.getEmail());
            comment.setComment(commentRequest.getComment());

            commentRepository.save(comment);

            return comment;
        }catch (Exception exception){

            throw new CustomException("Error while updating comment: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Integer commentId) {

        commentRepository.deleteById(commentId);
    }
}
