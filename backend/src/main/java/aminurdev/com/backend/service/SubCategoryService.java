package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.SubCategory;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface SubCategoryService {

    PaginationResponse<SubCategory> index(Sort.Direction direction, int page, int perPage);

    List<SubCategory> getAllSubCategories();

    SubCategory store(aminurdev.com.backend.domain.request.SubCategory subCategoryRequest);

    SubCategory edit(Integer subCategoryId);

    SubCategory update(Integer subCategoryId, aminurdev.com.backend.domain.request.SubCategory subCategoryRequest);

    void delete(Integer subCategoryId);
}
