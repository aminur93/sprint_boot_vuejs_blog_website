package aminurdev.com.backend.domain.repository;

import aminurdev.com.backend.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
}
