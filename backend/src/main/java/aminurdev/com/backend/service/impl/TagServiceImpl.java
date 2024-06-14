package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.Tag;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.TagRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {


    private final TagRepository tagRepository;


    @Override
    public PaginationResponse<Tag> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

        Page<Tag> tagPage = tagRepository.findAll(pageable);

        List<Tag> tags = tagPage.getContent();

        PaginationResponse<Tag> response = new PaginationResponse<>();

        response.setData(tags);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All tags fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(tagPage.getNumber() + 1);
        meta.setFrom(tagPage.getNumber() * tagPage.getSize() + 1);
        meta.setLastPage(tagPage.getTotalPages());
        meta.setPath("http://localhost:8080/api/v1/admin" + "/tag");
        meta.setPerPage(tagPage.getSize());
        meta.setTo((int) tagPage.getTotalElements());
        meta.setTotal((int) tagPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("http://localhost:8080/api/v1/admin" + "/tag?page=1");
        links.setLast("http://localhost:8080/api/v1/admin" + "/tag?page=" + tagPage.getTotalPages());
        if (tagPage.hasPrevious()) {
            links.setPrev("http://localhost:8080/api/v1/admin" + "/tag?page=" + tagPage.previousPageable().getPageNumber());
        }
        if (tagPage.hasNext()) {
            links.setNext("http://localhost:8080/api/v1/admin" + "/tag?page=" + tagPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<Tag> getAllTag() {

        List<Tag> tags = tagRepository.findAll();

        return tags;
    }

    @Override
    public Tag store(aminurdev.com.backend.domain.request.Tag tagRequest) {

        try{

            Tag tag = new Tag();

            tag.setName(tagRequest.getName());
            tag.setStatus(tagRequest.isStatus());

            tagRepository.save(tag);

            return tag;

        }catch (Exception exception){

            throw new CustomException("Error while creating tag:" + exception.getMessage(), exception);
        }
    }

    @Override
    public Tag edit(Integer tagId) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag is not found" + tagId));

        return tag;
    }

    @Override
    public Tag update(Integer tagId, aminurdev.com.backend.domain.request.Tag tagRequest) {

        try{

            Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag is not found" + tagId));

            tag.setName(tagRequest.getName());
            tag.setStatus(tagRequest.isStatus());

            tagRepository.save(tag);

            return tag;

        }catch (Exception exception){

            throw new CustomException("Error while creating tag:" + exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Integer tagId) {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag is not found" + tagId));

        if (tag.getId() != null)
        {
            tagRepository.delete(tag);

        }else {

            throw new RuntimeException("Something went wrong");
        }


    }
}
