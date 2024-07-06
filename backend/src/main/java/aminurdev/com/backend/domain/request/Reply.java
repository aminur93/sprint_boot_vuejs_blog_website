package aminurdev.com.backend.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply {

    private Integer id;

    private Integer comment_id;

    private String name;

    private String reply;
}
