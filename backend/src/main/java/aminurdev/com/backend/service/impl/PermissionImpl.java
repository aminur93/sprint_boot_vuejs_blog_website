package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Permission;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.PermissionRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.PermissionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissionImpl implements PermissionService {

    private final PermissionRepository permissionRepository;


    @Override
    public PaginationResponse<Permission> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

        Page<Permission> permissionPage = permissionRepository.findAll(pageable);

        List<Permission> permissions = permissionPage.getContent();

        PaginationResponse<Permission> response = new PaginationResponse<>();

        response.setData(permissions);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All permission fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(permissionPage.getNumber() + 1);
        meta.setFrom(permissionPage.getNumber() * permissionPage.getSize() + 1);
        meta.setLastPage(permissionPage.getTotalPages());
        meta.setPath("/permission");
        meta.setPerPage(permissionPage.getSize());
        meta.setTo((int) permissionPage.getTotalElements());
        meta.setTotal((int) permissionPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("/permission?page=1");
        links.setLast("/permission?page=" + permissionPage.getTotalPages());
        if (permissionPage.hasPrevious()) {
            links.setPrev("/permission?page=" + permissionPage.previousPageable().getPageNumber());
        }
        if (permissionPage.hasNext()) {
            links.setNext("/permission?page=" + permissionPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<Permission> getAllPermission() {
        List<Permission> permissions = permissionRepository.findAll();

        return permissions;
    }

    @Override
    public Permission store(aminurdev.com.backend.domain.request.Permission permissionRequest) {

        try{

            Permission permission = new Permission();

            permission.setName(permissionRequest.getName());

            permission = permissionRepository.save(permission);

            return permission;

        }catch (Exception e){

            throw new CustomException("Error while storing permission:" + e.getMessage(), e);
        }
    }

    @Override
    public Permission edit(Integer permissionId) {
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new ResourceNotFoundException("Permission not found"));

        return permission;
    }

    @Override
    public Permission update(aminurdev.com.backend.domain.request.Permission permissionRequest, Integer permissionId) {

        try{

            Permission permission = permissionRepository.findById(permissionId).orElseThrow(() -> new ResourceNotFoundException("Permission not found"));

            permission.setName(permissionRequest.getName());

            permission = permissionRepository.save(permission);

            return permission;

        }catch (Exception e){

            throw new CustomException("Error while update permission:" + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Integer permissionId) {

        permissionRepository.deleteById(permissionId);
    }
}
