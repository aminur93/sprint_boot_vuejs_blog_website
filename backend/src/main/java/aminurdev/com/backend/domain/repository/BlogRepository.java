package aminurdev.com.backend.domain.repository;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.domain.entity.Category;
import aminurdev.com.backend.domain.entity.SubCategory;
import aminurdev.com.backend.domain.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    Page<Blog> findByCategory(Category category, Pageable pageable);

    Page<Blog> findBySubCategory(SubCategory subCategory, Pageable pageable);

    Page<Blog> findByTags(Tag tag, Pageable pageable);
}
