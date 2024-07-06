package aminurdev.com.backend.controllers.rest.front;

import aminurdev.com.backend.domain.entity.Reply;
import aminurdev.com.backend.response.ResponseWrapper;
import aminurdev.com.backend.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@CrossOrigin(origins = "*", maxAge = 6000)
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/api/v1/public/reply")
    public ResponseEntity<ResponseWrapper> store(@Valid @RequestBody aminurdev.com.backend.domain.request.Reply replyRequest) {

        Reply reply = replyService.store(replyRequest);

        ResponseWrapper responseWrapper = new ResponseWrapper().success(
                Collections.singletonList(reply),
                "Reply store successful",
                "true",
                HttpStatus.CREATED.value()
        );

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(responseWrapper);
    }
}
