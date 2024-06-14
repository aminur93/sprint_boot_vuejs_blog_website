package aminurdev.com.backend.domain.request;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    private Integer id;

    private Integer category_id;

    private Integer sub_category_id;

    private List<Integer> tag_ids;

    @NotNull(message = " Author field is required")
    private String author;

    @NotNull(message = " Title field is required")
    private String title;

    @NotNull(message = " Slogan field is required")
    private String slogan;

    private String slug;

    @Lob
    @NotNull(message = "Description field is required")
    private String description;

    @NotNull(message = "Date must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private MultipartFile image;

    private boolean status;
}
