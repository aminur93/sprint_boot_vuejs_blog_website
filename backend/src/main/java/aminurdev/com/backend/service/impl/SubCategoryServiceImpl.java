package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.Role;
import aminurdev.com.backend.domain.entity.SubCategory;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.CategoryRepository;
import aminurdev.com.backend.domain.repository.SubCategoryRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.SubCategoryService;
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
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public PaginationResponse<SubCategory> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

        Page<SubCategory> subCategoryPage = subCategoryRepository.findAll(pageable);

        List<SubCategory> subCategories = subCategoryPage.getContent();

        PaginationResponse<SubCategory> response = new PaginationResponse<>();

        response.setData(subCategories);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All sub categories fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(subCategoryPage.getNumber() + 1);
        meta.setFrom(subCategoryPage.getNumber() * subCategoryPage.getSize() + 1);
        meta.setLastPage(subCategoryPage.getTotalPages());
        meta.setPath("/sub-category");
        meta.setPerPage(subCategoryPage.getSize());
        meta.setTo((int) subCategoryPage.getTotalElements());
        meta.setTotal((int) subCategoryPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("/sub-category?page=1");
        links.setLast("/sub-category?page=" + subCategoryPage.getTotalPages());
        if (subCategoryPage.hasPrevious()) {
            links.setPrev("/sub-category?page=" + subCategoryPage.previousPageable().getPageNumber());
        }
        if (subCategoryPage.hasNext()) {
            links.setNext("/sub-category?page=" + subCategoryPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<SubCategory> getAllSubCategories() {

        List<SubCategory> subCategories = subCategoryRepository.findAll();

        return subCategories;
    }

    @Override
    public SubCategory store(aminurdev.com.backend.domain.request.SubCategory subCategoryRequest) {

        try{

            SubCategory subCategory = new SubCategory();

            if (subCategoryRequest.getCategory_id() != null) {
                Category category = categoryRepository.findById(subCategoryRequest.getCategory_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                // Set the fetched category to the saved sub-category
                subCategory.setCategory(category);
            }

            subCategory.setName(subCategoryRequest.getName());
            subCategory.setDescription(subCategoryRequest.getDescription());
            subCategory.setStatus(subCategoryRequest.isStatus());

            subCategoryRepository.save(subCategory);

            return subCategory;

        }catch (Exception exception){

            throw new CustomException("Error while creating sub category: " + exception.getMessage(), exception);
        }
    }

    @Override
    public SubCategory edit(Integer subCategoryId) {

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFoundException("Sub category not found!"));

        return subCategory;
    }

    @Override
    public SubCategory update(Integer subCategoryId, aminurdev.com.backend.domain.request.SubCategory subCategoryRequest) {

        try{

            SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFoundException("Sub category not found!"));

            if (subCategoryRequest.getCategory_id() != null) {
                Category category = categoryRepository.findById(subCategoryRequest.getCategory_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                // Set the fetched category to the saved sub-category
                subCategory.setCategory(category);
            }

            subCategory.setName(subCategoryRequest.getName());
            subCategory.setDescription(subCategoryRequest.getDescription());
            subCategory.setStatus(subCategoryRequest.isStatus());

            subCategoryRepository.save(subCategory);

            return subCategory;

        }catch (Exception exception){

            throw new CustomException("Error while creating sub category: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Integer subCategoryId) {

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(() -> new ResourceNotFoundException("Sub category not found!"));

        if (subCategory.getId() != null)
        {
            subCategoryRepository.deleteById(subCategoryId);
        }else {

            throw new ResourceNotFoundException("Sub category not found!");
        }

    }
}
