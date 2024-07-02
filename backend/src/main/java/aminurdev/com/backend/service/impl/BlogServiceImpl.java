package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.SubCategory;
import aminurdev.com.backend.domain.entity.Tag;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.BlogRepository;
import aminurdev.com.backend.domain.repository.CategoryRepository;
import aminurdev.com.backend.domain.repository.SubCategoryRepository;
import aminurdev.com.backend.domain.repository.TagRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    private final CategoryRepository categoryRepository;

    private final SubCategoryRepository subCategoryRepository;

    private final TagRepository tagRepository;

    private static final String  RESOURCE_DIRECTORY = "./images/";

    @Override
    public PaginationResponse<Blog> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

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
        meta.setPath("http://localhost:8080/api/v1/admin" +"/blog");
        meta.setPerPage(blogPage.getSize());
        meta.setTo((int) blogPage.getTotalElements());
        meta.setTotal((int) blogPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("http://localhost:8080/api/v1/admin" +"/blog?page=1");
        links.setLast("http://localhost:8080/api/v1/admin" +"/blog?page=" + blogPage.getTotalPages());
        if (blogPage.hasPrevious()) {
            links.setPrev("http://localhost:8080/api/v1/admin" +"/blog?page=" + blogPage.previousPageable().getPageNumber());
        }
        if (blogPage.hasNext()) {
            links.setNext("http://localhost:8080/api/v1/admin" +"/blog?page=" + blogPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<Blog> getAllBlogs() {

        List<Blog> blogs = blogRepository.findAll();

        return blogs;
    }

    @Override
    public Blog store(aminurdev.com.backend.domain.request.Blog blogRequest) {

        try {

            Blog blog = new Blog();

            String imageName = null;
            imageName = saveImageFile(blogRequest.getImage());

            String slugName = blog.generateSlug(blogRequest.getTitle());

            // fetch category
            Category category = categoryRepository.findById(blogRequest.getCategory_id()).orElseThrow(() -> new ResourceNotFoundException("Category" + "id" + blogRequest.getCategory_id()));
            blog.setCategory(category);

            // fetch sub category
            SubCategory subCategory = subCategoryRepository.findById(blogRequest.getSub_category_id()).orElseThrow(() -> new ResourceNotFoundException("SubCategory" + "id" + blogRequest.getSub_category_id()));
            blog.setSubCategory(subCategory);

            // fetch tags
            List<Integer> tagIds = blogRequest.getTag_ids();
            List<Tag> tags = tagRepository.findAllById(tagIds);
            blog.setTags(tags);

            blog.setAuthor(blogRequest.getAuthor());
            blog.setTitle(blogRequest.getTitle());
            blog.setSlogan(blogRequest.getSlogan());
            blog.setDescription(blogRequest.getDescription());
            blog.setSlug(slugName);
            blog.setImage(imageName);
            blog.setStatus(blogRequest.isStatus());
            blog.setDate(blogRequest.getDate());

            blogRepository.save(blog);

            return blog;

        }catch (Exception exception) {

            throw new CustomException("Error while creating blog:" + exception.getMessage(), exception);
        }
    }

    @Override
    public Blog edit(Integer blogId) {

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog is not found" + blogId));

        return blog;
    }

    @Override
    public Blog update(Integer blogId, aminurdev.com.backend.domain.request.Blog blogRequest) {

        try{

            Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog is not found" + blogId));

            if (blogRequest.getImage() != null)
            {
                deleteImageFile(blog.getImage());

                String imageName = saveImageFile(blogRequest.getImage());
                blog.setImage(imageName);
            }else {

                blog.setImage(blog.getImage());
            }

            String slugName = blog.generateSlug(blogRequest.getTitle());

            // fetch category
            Category category = categoryRepository.findById(blogRequest.getCategory_id()).orElseThrow(() -> new ResourceNotFoundException("Category" + "id" + blogRequest.getCategory_id()));
            blog.setCategory(category);

            // fetch sub category
            SubCategory subCategory = subCategoryRepository.findById(blogRequest.getSub_category_id()).orElseThrow(() -> new ResourceNotFoundException("SubCategory" + "id" + blogRequest.getSub_category_id()));
            blog.setSubCategory(subCategory);

            //clear fetch tag
            blog.getTags().clear();

            // fetch tags
            List<Integer> tagIds = blogRequest.getTag_ids();
            List<Tag> tags = tagRepository.findAllById(tagIds);
            blog.setTags(tags);

            blog.setAuthor(blogRequest.getAuthor());
            blog.setTitle(blogRequest.getTitle());
            blog.setSlogan(blogRequest.getSlogan());
            blog.setDescription(blogRequest.getDescription());
            blog.setSlug(slugName);
            blog.setStatus(blogRequest.isStatus());

            blogRepository.save(blog);

            return blog;

        }catch (Exception exception) {

            throw new CustomException("Error while creating blog:" + exception.getMessage(), exception);
        }
    }

    @Override
    public void destroy(Integer blogId) {

        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> new ResourceNotFoundException("Blog is not found" + blogId));
        deleteImageFile(blog.getImage());

        // clear fetch tag
        blog.getTags().clear();

        blogRepository.delete(blog);
    }

    private String saveImageFile(MultipartFile image) {

        String imageName = generateUniqueImageName(Objects.requireNonNull(image.getOriginalFilename()));

        byte[] imageData = new byte[0];
        try {
            imageData = image.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String filePath = RESOURCE_DIRECTORY + imageName;
        try {
            Files.write(Paths.get(filePath), imageData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageName;
    }

    private String generateUniqueImageName(String originalFilename){

        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return UUID.randomUUID().toString() + extension;
    }

    private void deleteImageFile(String imageName) {

        if (imageName != null)
        {
            String imagePath = RESOURCE_DIRECTORY + File.separator + imageName;

            // Create a File object representing the image file
            File imageFile = new File(imagePath);

            // Check if the file exists and delete it if it does
            if (imageFile.exists()) {
                if (imageFile.delete()) {
                    System.out.println("Deleted image file: " + imageName);
                } else {
                    System.err.println("Failed to delete image file: " + imageName);
                }
            } else {
                System.err.println("Image file not found: " + imageName);
            }
        }
    }
}
