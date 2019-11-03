package xyz.majorkevin.bbs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.MyUserDetail;
import xyz.majorkevin.bbs.entity.Post;
import xyz.majorkevin.bbs.entity.UserVote;
import xyz.majorkevin.bbs.service.PostService;
import xyz.majorkevin.bbs.service.VoteService;

import java.util.List;

/**
 * index page controller
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private VoteService voteService;

    @GetMapping("/")
    public String index(Model model){
        List<Post> allPosts = postService.findAllWithBriefContent();
        logger.info("get posts: " + allPosts);
        model.addAttribute("allPosts", allPosts);
        return "index";
    }

    @GetMapping("/checkpost/{postId}")
    public String getThePost(@PathVariable("postId") Long postId, Model model, @AuthenticationPrincipal MyUserDetail myUserDetail){
        Post thePost = postService.findById(postId);
        List<Comment> thePostComments = thePost.getComments();
        UserVote userVote = null;
        if(myUserDetail != null){
            userVote = voteService.getUserVoteData(myUserDetail.getUser());
        }
        if(userVote == null){
            // user doesn't have vote data, then make it default
            userVote = new UserVote("Guest", new Long[0], new Long[0]);
        }

        model.addAttribute("thePost", thePost);
        model.addAttribute("theComments", thePostComments);
        model.addAttribute("userVoteData", userVote);
        return "show-post";
    }
}
