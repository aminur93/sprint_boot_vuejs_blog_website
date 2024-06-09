package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.domain.entity.Role;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.PermissionRepository;
import aminurdev.com.backend.domain.repository.RoleRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    @Override
    public PaginationResponse<Role> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

        Page<Role> rolePage = roleRepository.findAll(pageable);

        List<Role> roles = rolePage.getContent();

        PaginationResponse<Role> response = new PaginationResponse<>();

        response.setData(roles);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All role fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(rolePage.getNumber() + 1);
        meta.setFrom(rolePage.getNumber() * rolePage.getSize() + 1);
        meta.setLastPage(rolePage.getTotalPages());
        meta.setPath("/role");
        meta.setPerPage(rolePage.getSize());
        meta.setTo((int) rolePage.getTotalElements());
        meta.setTotal((int) rolePage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("/role?page=1");
        links.setLast("/role?page=" + rolePage.getTotalPages());
        if (rolePage.hasPrevious()) {
            links.setPrev("/role?page=" + rolePage.previousPageable().getPageNumber());
        }
        if (rolePage.hasNext()) {
            links.setNext("/role?page=" + rolePage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<Role> getAllRole() {

        List<Role> roles = roleRepository.findAll();

        return roles;
    }

    @Override
    public Role store(aminurdev.com.backend.domain.request.Role roleRequest) {

        try{

            // Create a new Role entity with the provided name
            Role role = Role.builder()
                    .name(roleRequest.getName())
                    .build();

            // Save the Role to the database to obtain its ID
            Role savedRole = roleRepository.save(role);

            // Fetch permission entities based on permission IDs from RoleRequest
            List<Permission> permissions = permissionRepository.findAllById(roleRequest.getPermissions());

            // Set the fetched permissions to the saved Role
            savedRole.setPermissions(permissions);

            // Save the updated Role with associations to the database
            return roleRepository.save(savedRole);

        }catch (Exception exception){

            throw new CustomException("Error while storing role: " + exception.getMessage(), exception);
        }

    }

    @Override
    public Role edit(Integer roleId) {

        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + roleId));

        return role;
    }

    @Transactional
    @Override
    public Role update(aminurdev.com.backend.domain.request.Role roleRequest, Integer roleId) {

        try{

            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new CustomException("Role not found with ID: " + roleId));

            // Update role name if provided in the request
            if (roleRequest.getName() != null) {
                role.setName(roleRequest.getName());
            }

            // Clear existing permissions
            role.getPermissions().clear();

            // Fetch permission entities based on permission IDs from RoleRequest
            List<Permission> newPermissions = permissionRepository.findAllById(roleRequest.getPermissions());

            // Set the fetched permissions to the role
            role.getPermissions().addAll(newPermissions);

            // Save the updated role with permissions
            return roleRepository.save(role);

        }catch (Exception exception){

            throw new CustomException("Error while updated role: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Integer roleId) {

        try {
            // Get the role from the database
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new CustomException("Role not found with ID: " + roleId));

            if (role.getId() != null) {
                // Delete all permissions associated with the role
                roleRepository.deleteRolePermissions(roleId);
            }

            // Delete the role from the database
            roleRepository.delete(role);
        } catch (Exception exception) {
            throw new CustomException("Error while deleting role: " + exception.getMessage(), exception);
        }
    }
}
