package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

public interface FrontService {

    PaginationResponse<Blog> getBlogs(Sort.Direction direction, int page, int perPage);

    Blog getBlogDetails(Integer blogId);
}
