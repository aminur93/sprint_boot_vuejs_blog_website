package aminurdev.com.backend.domain.repository;

import aminurdev.com.backend.domain.entity.Menu;
import aminurdev.com.backend.domain.entity.MenuDropdown;
import aminurdev.com.backend.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT DISTINCT m FROM Menu m LEFT JOIN FETCH m.menuDropdowns")
    List<Menu> findAllWithMenuDropdowns();
}
