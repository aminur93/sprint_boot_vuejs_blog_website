package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BlogService {

    PaginationResponse<Blog> index(Sort.Direction direction, int page, int perPage);

    List<Blog> getAllBlogs();

    Blog store(aminurdev.com.backend.domain.request.Blog blogRequest);

    Blog edit(Integer blogId);

    Blog update(Integer blogId, aminurdev.com.backend.domain.request.Blog blogRequest);

    void destroy(Integer blogId);
}
