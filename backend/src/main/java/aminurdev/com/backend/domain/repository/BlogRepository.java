package aminurdev.com.backend.domain.repository;

import aminurdev.com.backend.domain.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
}
