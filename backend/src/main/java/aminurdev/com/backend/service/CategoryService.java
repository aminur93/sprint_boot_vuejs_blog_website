package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryService {

    PaginationResponse<Category> index(Sort.Direction direction, int page, int perPage);

    List<Category> getAllCategories();

    Category store(aminurdev.com.backend.domain.request.Category categoryRequest);

    Category edit(Integer categoryId);

    Category update(Integer categoryId, aminurdev.com.backend.domain.request.Category categoryRequest);

    void destroy(Integer categoryId);
}
