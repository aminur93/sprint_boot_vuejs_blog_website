package aminurdev.com.backend.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDropdown {

    private Integer id;

    private Integer menu_id;

    private Integer permission_id;

    private String title;

    private String icon;

    private String route;
}
