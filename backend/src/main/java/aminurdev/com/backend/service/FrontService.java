package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.SubCategory;
import aminurdev.com.backend.domain.entity.Tag;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface FrontService {

    PaginationResponse<Blog> getBlogs(Sort.Direction direction, int page, int perPage);

    Blog getBlogDetails(Integer blogId);

    List<Category> getAllCategories();

    List<SubCategory> getAllSubCategories();

    List<Tag> getAllTags();
}
