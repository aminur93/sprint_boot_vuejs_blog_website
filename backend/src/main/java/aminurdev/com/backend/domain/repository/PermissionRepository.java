package aminurdev.com.backend.domain.repository;

import aminurdev.com.backend.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

    @Query("SELECT p FROM Permission p WHERE p.id IN :ids")
    List<Permission> findAllById(@Param("ids") List<Integer> ids);
}
