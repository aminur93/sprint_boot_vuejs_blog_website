package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.BlogRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.FrontService;
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
public class FrontServiceImpl implements FrontService {

    private final BlogRepository blogRepository;

    @Override
    public PaginationResponse<Blog> getBlogs(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"id"));

        Page<Blog> blogPage = blogRepository.findAll(pageable);

        List<Blog> blogs = blogPage.getContent();

        PaginationResponse<Blog> response = new PaginationResponse<>();

        response.setData(blogs);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All blogs fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(blogPage.getNumber() + 1);
        meta.setFrom(blogPage.getNumber() * blogPage.getSize() + 1);
        meta.setLastPage(blogPage.getTotalPages());
        meta.setPath("http://localhost:8080/api/v1/public" +"/blog");
        meta.setPerPage(blogPage.getSize());
        meta.setTo((int) blogPage.getTotalElements());
        meta.setTotal((int) blogPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("http://localhost:8080/api/v1/public" +"/blog?page=1");
        links.setLast("http://localhost:8080/api/v1/public" +"/blog?page=" + blogPage.getTotalPages());
        if (blogPage.hasPrevious()) {
            links.setPrev("http://localhost:8080/api/v1/public" +"/blog?page=" + blogPage.previousPageable().getPageNumber());
        }
        if (blogPage.hasNext()) {
            links.setNext("http://localhost:8080/api/v1/public" +"/blog?page=" + blogPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public Blog getBlogDetails(Integer blogId) {

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog is not found" + blogId));

        return blog;
    }
}
