package aminurdev.com.backend.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    private Integer id;

    private Integer permission_id;

    private String title;

    private String icon;

    private String route;

    private boolean header_menu;

    private boolean sidebar_menu;

    private boolean dropdown;

}
