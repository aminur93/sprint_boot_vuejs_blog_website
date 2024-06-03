package aminurdev.com.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper {

    private List<Object> errors;
    private List<Object> data;

    private String message;

    private String success;

    private Integer status;

    public ResponseWrapper success(List<Object> data, String message, String success, Integer status) {

        this.data = data;
        this.errors = null;
        this.message = message;
        this.success = success;
        this.status = status;

        return this;
    }

    public ResponseWrapper error(List<Object> errors, String message, String success, Integer status) {
        this.data = null;
        this.errors = errors;
        this.message = message;
        this.success = success;
        this.status = status;

        return this;
    }
}
