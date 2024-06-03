package aminurdev.com.backend.response.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meta {

    private int currentPage;
    private int from;
    private int lastPage;
    private String path;
    private int perPage;
    private int to;
    private int total;
}
