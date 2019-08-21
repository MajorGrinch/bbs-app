package xyz.majorkevin.bbs.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.MyUserDetail;
import xyz.majorkevin.bbs.entity.Reply;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.ReplyCommentForm;
import xyz.majorkevin.bbs.form.ReplyForm;
import xyz.majorkevin.bbs.rest.exception.CommentNotFoundException;
import xyz.majorkevin.bbs.rest.exception.ReplyFormException;
import xyz.majorkevin.bbs.rest.exception.UserNotFoundException;
import xyz.majorkevin.bbs.service.CommentService;
import xyz.majorkevin.bbs.service.ReplyService;
import xyz.majorkevin.bbs.service.UserService;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);

    @GetMapping("/comment/{commentId}")
    public List<Reply> getCommentRelies(@PathVariable Long commentId){
        Comment theComment = commentService.findById(commentId);
        if(theComment == null){
            throw new CommentNotFoundException("Comment Not Found");
        }
        return replyService.findByComment(theComment);
    }

    @PostMapping("/comment")
    public Reply replyTheComment(@RequestBody ReplyCommentForm replyCommentForm, @AuthenticationPrincipal MyUserDetail userDetail){

        /**
         * validate the form
         */
        if(replyCommentForm.getCommentId() == null || replyCommentForm.getReplyContent().length() == 0){
            throw new CommentNotFoundException("Form error");
        }

        if(userDetail == null){
            throw new UserNotFoundException("Auth failed");
        }
        Comment theComment = commentService.findById(replyCommentForm.getCommentId());
        if(theComment == null){
            throw new CommentNotFoundException("Comment Not Found");
        }
        User currUser = userDetail.getUser();
        logger.info("reply the comment " + theComment.getCreatedByUser());
        logger.info(replyCommentForm.getReplyContent());
        Reply theReply = replyCommentForm.toReply(theComment, currUser, theComment.getCreatedByUser(), new Date());
        replyService.save(theReply);
        logger.info(theReply.toString());
        return theReply;
    }

    @PostMapping("/reply")
    public Reply replyTheReply(@RequestBody ReplyForm replyForm, @AuthenticationPrincipal MyUserDetail myUserDetail){

        /**
         * validate the form
         */
        if(replyForm.getCommentId() == null || replyForm.getToUserId() == null || replyForm.getReplyContent().length() == 0){
            throw new ReplyFormException("Form Error");
        }
        if(myUserDetail == null){
            throw new UserNotFoundException("Auth failed");
        }
        Comment theComment = commentService.findById(replyForm.getCommentId());
        if(theComment == null){
            throw new CommentNotFoundException("Comment Not Found");
        }
        User currUser = myUserDetail.getUser();
        User toUser = userService.findById(replyForm.getToUserId());
        Reply theReply = replyForm.toReply(theComment, currUser, toUser, new Date());
        replyService.save(theReply);
        return theReply;
    }
}
