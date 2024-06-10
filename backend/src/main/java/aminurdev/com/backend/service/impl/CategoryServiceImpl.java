package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.User;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.CategoryRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public PaginationResponse<Category> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<Category> categories = categoryPage.getContent();

        PaginationResponse<Category> response = new PaginationResponse<>();

        response.setData(categories);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All categories fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(categoryPage.getNumber() + 1);
        meta.setFrom(categoryPage.getNumber() * categoryPage.getSize() + 1);
        meta.setLastPage(categoryPage.getTotalPages());
        meta.setPath("/category");
        meta.setPerPage(categoryPage.getSize());
        meta.setTo((int) categoryPage.getTotalElements());
        meta.setTotal((int) categoryPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("/category?page=1");
        links.setLast("/category?page=" + categoryPage.getTotalPages());
        if (categoryPage.hasPrevious()) {
            links.setPrev("/category?page=" + categoryPage.previousPageable().getPageNumber());
        }
        if (categoryPage.hasNext()) {
            links.setNext("/category?page=" + categoryPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<Category> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories;
    }

    @Override
    public Category store(aminurdev.com.backend.domain.request.Category categoryRequest) {

        try{

            Category category = new Category();

            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            category.setStatus(categoryRequest.isStatus());

            categoryRepository.save(category);

            return category;

        }catch (Exception exception){

            throw new CustomException("Error while creating category: " + exception.getMessage(), exception);
        }
    }

    @Override
    public Category edit(Integer categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" + "id" + categoryId));

        return category;
    }

    @Override
    public Category update(Integer categoryId, aminurdev.com.backend.domain.request.Category categoryRequest) {
        try{

            Category category =categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" + "id" + categoryId));

            category.setName(categoryRequest.getName());
            category.setDescription(categoryRequest.getDescription());
            category.setStatus(categoryRequest.isStatus());

            categoryRepository.save(category);

            return category;

        }catch (Exception exception){

            throw new CustomException("Error while creating category: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void destroy(Integer categoryId) {

        categoryRepository.deleteById(categoryId);
    }
}
