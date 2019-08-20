package xyz.majorkevin.bbs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.majorkevin.bbs.entity.MyUserDetail;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.VoteComment;
import xyz.majorkevin.bbs.service.UserService;
import xyz.majorkevin.bbs.service.VoteService;

@RestController
@RequestMapping("/vote")
public class VoteController {

    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @PostMapping
    public String voteComment(@RequestBody VoteComment voteComment, @AuthenticationPrincipal MyUserDetail myUserDetail){
        logger.info(voteComment.toString());
        if(myUserDetail == null){
            return "Login needed";
        }
        User user = myUserDetail.getUser();
        String userVoteId = user.getVoteDataId();
        if(userVoteId == null){
            userVoteId = voteService.insertUserVoteData(user.getUsername());
            user.setVoteDataId(userVoteId);
            userService.save(user);
        }
        if(voteComment.getActionCode() == 1){
            logger.info(userVoteId);
            voteService.upvote(userVoteId, voteComment.getCommentId());
        }
        if(voteComment.getActionCode() == 2){
            voteService.downvote(userVoteId, voteComment.getCommentId());
        }
        return "DONE";
    }

}
