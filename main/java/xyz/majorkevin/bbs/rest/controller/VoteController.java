package xyz.majorkevin.bbs.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.MyUserDetail;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.VoteComment;
import xyz.majorkevin.bbs.rest.exception.CommentNotFoundException;
import xyz.majorkevin.bbs.rest.exception.UserNotFoundException;
import xyz.majorkevin.bbs.rest.exception.VoteException;
import xyz.majorkevin.bbs.service.CommentService;
import xyz.majorkevin.bbs.service.UserService;
import xyz.majorkevin.bbs.service.VoteService;

import javax.validation.Valid;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    public Comment voteComment(@RequestBody VoteComment voteComment, @AuthenticationPrincipal MyUserDetail myUserDetail){
        logger.info(voteComment.toString());

        if(voteComment.getCommentId() == null || voteComment.getActionCode() == null){
            throw new VoteException("Form Error");
        }

        /**
         * user need to login to vote a specific comment
         */
        if(myUserDetail == null){
            throw new UserNotFoundException("Auth failed");
        }

        Comment theComment = commentService.findById(voteComment.getCommentId());

        if(theComment == null){
            throw new CommentNotFoundException("Comment Not Found");
        }

        User user = myUserDetail.getUser();
        String userVoteId = user.getVoteDataId();
        if(userVoteId == null){
            userVoteId = voteService.insertUserVoteData(user.getUsername());
            user.setVoteDataId(userVoteId);
            userService.save(user);
        }

        // TODO refactor the upvote and downvote to accept a comment object instead of a id
        if(voteComment.getActionCode() == 1){
            logger.info(userVoteId);
            voteService.upvote(userVoteId, voteComment.getCommentId());
        }
        if(voteComment.getActionCode() == 2){
            voteService.downvote(userVoteId, voteComment.getCommentId());
        }
        return theComment;
    }

}
