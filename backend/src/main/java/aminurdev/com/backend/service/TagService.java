package aminurdev.com.backend.service;

import aminurdev.com.backend.domain.entity.Tag;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface TagService {

    PaginationResponse<Tag> index(Sort.Direction direction, int page, int perPage);

    List<Tag> getAllTag();

    Tag store(aminurdev.com.backend.domain.request.Tag tagRequest);

    Tag edit(Integer tagId);

    Tag update(Integer tagId, aminurdev.com.backend.domain.request.Tag tagRequest);

    void delete(Integer tagId);
}
