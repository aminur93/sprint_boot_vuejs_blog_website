package aminurdev.com.backend.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    private Integer id;

    private Integer blog_id;

    private String name;

    private String email;

    private String comment;
}
