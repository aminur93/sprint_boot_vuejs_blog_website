package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Role;
import aminurdev.com.backend.domain.entity.User;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.RoleRepository;
import aminurdev.com.backend.domain.repository.UserRepository;
import aminurdev.com.backend.response.pagination.Links;
import aminurdev.com.backend.response.pagination.Meta;
import aminurdev.com.backend.response.pagination.PaginationResponse;
import aminurdev.com.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public PaginationResponse<User> index(Sort.Direction direction, int page, int perPage) {

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(direction,"updatedAt"));

        Page<User> userPage = userRepository.findAll(pageable);

        List<User> users = userPage.getContent();

        PaginationResponse<User> response = new PaginationResponse<>();

        response.setData(users);
        response.setSuccess(true);
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All user fetch successful");

        Meta meta = new Meta();

        meta.setCurrentPage(userPage.getNumber() + 1);
        meta.setFrom(userPage.getNumber() * userPage.getSize() + 1);
        meta.setLastPage(userPage.getTotalPages());
        meta.setPath("/user");
        meta.setPerPage(userPage.getSize());
        meta.setTo((int) userPage.getTotalElements());
        meta.setTotal((int) userPage.getTotalElements());
        response.setMeta(meta);

        Links links = new Links();

        links.setFirst("/user?page=1");
        links.setLast("/user?page=" + userPage.getTotalPages());
        if (userPage.hasPrevious()) {
            links.setPrev("/user?page=" + userPage.previousPageable().getPageNumber());
        }
        if (userPage.hasNext()) {
            links.setNext("/user?page=" + userPage.nextPageable().getPageNumber());
        }

        response.setLinks(links);

        return response;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = userRepository.findAll();

        return users;
    }

    @Override
    public User store(aminurdev.com.backend.domain.request.User userRequest) {
        try {

            // Create a new User entity with the provided name
            User user = User.builder()
                    .name(userRequest.getName())
                    .email(userRequest.getEmail())
                    .password(passwordEncoder.encode(userRequest.getPassword()))
                    .build();

            // Save the User to the database to obtain its ID
            User savedUser = userRepository.save(user);

            // Fetch role entities based on role IDs from RoleRequest
            if (userRequest.getRole() != null) {
                Role role = roleRepository.findById(userRequest.getRole())
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

                // Set the fetched roles to the saved User
                savedUser.setRoles(role);
            }

            // Save the updated User with associations to the database
            return userRepository.save(savedUser);

        }catch (Exception exception) {
            throw new CustomException("Error while creating user: " + exception.getMessage(), exception);
        }
    }

    @Override
    public User edit(Integer userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        return user;
    }

    @Override
    public User update(Integer userId, aminurdev.com.backend.domain.request.User userRequest) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

            user.setName(userRequest.getName());
            user.setEmail(userRequest.getEmail());

            if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
                // New password provided, encode and set it
                user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            } else {
                // No new password provided, keep the old password
                user.setPassword(user.getPassword()); // This is essentially a no-op
            }

            // Save the User to the database to obtain its ID
            User savedUser = userRepository.save(user);

            if (userRequest.getRole() != null) {
                Role role = roleRepository.findById(userRequest.getRole())
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

                // Set the fetched roles to the saved User
                savedUser.setRoles(role);
            }

            // Save the updated User with associations to the database
            return userRepository.save(savedUser);

        }catch (Exception exception){

            throw new CustomException("Error while updating user: " + exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Integer userId) {
        try{
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

            if (user.getId() != null) {
                // Delete all roles associated with the user
                userRepository.deleteUserRoles(userId);
            }

            userRepository.deleteById(userId);
        }catch (Exception exception){

            throw new CustomException("Error while deleting user: " + exception.getMessage(), exception);
        }

    }
}
