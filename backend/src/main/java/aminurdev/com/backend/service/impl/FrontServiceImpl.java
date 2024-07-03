package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.*;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.*;
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

    private final CategoryRepository categoryRepository;

    private final SubCategoryRepository subCategoryRepository;

    private final TagRepository tagRepository;

    private final NewsLetterRepository newsLetterRepository;

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

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category is not found" + categoryId));
    }

    @Override
    public List<SubCategory> getAllSubCategories() {

        return subCategoryRepository.findAll();
    }

    @Override
    public List<Tag> getAllTags() {

        return tagRepository.findAll();
    }

    @Override
    public NewsLetter StoreNewsLetter(aminurdev.com.backend.domain.request.NewsLetter newsLetterRequest) {

        try{

            NewsLetter newsLetter = new NewsLetter();

            newsLetter.setEmail(newsLetterRequest.getEmail());

            newsLetterRepository.save(newsLetter);

            return newsLetter;

        }catch (Exception exception){

            throw new CustomException("Error while creating news letter:" + exception.getMessage(), exception);
        }
    }

    @Override
    public PaginationResponse<Blog> getCategoryBlogs(Category category, Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"id"));

        Page<Blog> blogPage = blogRepository.findByCategory(category,pageable);

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
}
