package aminurdev.com.backend.service.impl;

import aminurdev.com.backend.domain.entity.Comment;
import aminurdev.com.backend.domain.entity.Reply;
import aminurdev.com.backend.domain.exception.CustomException;
import aminurdev.com.backend.domain.exception.ResourceNotFoundException;
import aminurdev.com.backend.domain.repository.CommentRepository;
import aminurdev.com.backend.domain.repository.ReplyRepository;
import aminurdev.com.backend.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;

    private final CommentRepository commentRepository;

    @Override
    public Reply store(aminurdev.com.backend.domain.request.Reply replyRequest) {

        try {

            Reply reply = new Reply();

            Comment comment = commentRepository.findById(replyRequest.getComment_id()).orElseThrow(() -> new ResourceNotFoundException("Comment is not found" + replyRequest.getComment_id()));

            reply.setComment(comment);
            reply.setName(replyRequest.getName());
            reply.setReply(replyRequest.getReply());

            replyRepository.save(reply);

            return reply;

        }catch (Exception exception){

            throw new CustomException("Error while creating reply: " + exception.getMessage(), exception);
        }
    }
}
