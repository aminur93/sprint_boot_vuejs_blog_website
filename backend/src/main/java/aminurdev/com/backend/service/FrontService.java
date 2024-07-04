package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.*;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface FrontService {

    PaginationResponse<Blog> getBlogs(Sort.Direction direction, int page, int perPage);

    Blog getBlogDetails(Integer blogId);

    List<Category> getAllCategories();

    Category getCategory(Integer categoryId);

    List<SubCategory> getAllSubCategories();

    SubCategory getSubCategory(Integer subCategoryId);

    List<Tag> getAllTags();

    Tag getTag(Integer tagId);

    NewsLetter StoreNewsLetter(aminurdev.com.backend.domain.request.NewsLetter newsLetterRequest);

    PaginationResponse<Blog> getCategoryBlogs(Category category, Sort.Direction direction, int page, int perPage);

    PaginationResponse<Blog> getSubCategoryBlogs(SubCategory subCategory, Sort.Direction direction, int page, int perPage);

    PaginationResponse<Blog> getBlogsByTag(Tag tag, Sort.Direction direction, int page, int perPage);

    ContactUs storeContactUs(aminurdev.com.backend.domain.request.ContactUs contactUsRequest);
}
