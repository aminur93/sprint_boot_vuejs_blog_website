package aminurdev.com.backend.controllers.rest.admin;

import aminurdev.com.backend.domain.entity.Blog;
import aminurdev.com.backend.domain.repository.BlogRepository;
import aminurdev.com.backend.domain.repository.CategoryRepository;
import aminurdev.com.backend.domain.repository.TagRepository;
import aminurdev.com.backend.domain.repository.UserRepository;
import aminurdev.com.backend.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 6000)
@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final BlogRepository blogRepository;

    private final TagRepository tagRepository;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasAuthority('dashboard')")
    public ResponseEntity<ResponseWrapper> index()
    {
        Long blog = blogRepository.count();

        Long tag = tagRepository.count();

        Long category = categoryRepository.count();

        Long user = userRepository.count();

        Map<String, Object> responseData = new HashMap<>();

        responseData.put("blog", blog);
        responseData.put("tag", tag);
        responseData.put("category", category);
        responseData.put("user", user);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                responseData,
                "Fetch successful",
                "True",
                HttpStatus.OK.value()
        );

        return ResponseEntity.ok(responseWrapper);
    }
}
