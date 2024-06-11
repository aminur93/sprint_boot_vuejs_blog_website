package aminurdev.com.backend.domain.repository;

import aminurdev.com.backend.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
