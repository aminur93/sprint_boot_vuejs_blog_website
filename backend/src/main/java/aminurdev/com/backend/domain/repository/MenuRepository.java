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

    List<Menu> findByPermissionIn(Collection<Permission> permissions);

    @Query("SELECT md FROM MenuDropdown md JOIN FETCH md.menu m WHERE m.id IN :menuIds AND md.permission IN :permissions")
    List<MenuDropdown> findMenuDropdownsByMenuIdsAndPermissions(@Param("menuIds") Collection<Integer> menuIds, @Param("permissions") Collection<Permission> permissions);


    // Custom query to fetch menus by permission
    @Query("SELECT m FROM Menu m WHERE m.permission.id = :permissionId OR m.permission IS NULL")
    List<Menu> findMenusByPermission(@Param("permissionId") Integer permissionId);

    // Custom query to fetch menu dropdowns by permission and menu ID
    @Query("SELECT md FROM MenuDropdown md JOIN FETCH md.menu m WHERE m.id = :menuId AND md.permission.id = :permissionId")
    List<MenuDropdown> findMenuDropdownsByMenuIdAndPermission(@Param("menuId") Integer menuId, @Param("permissionId") Integer permissionId);
}
