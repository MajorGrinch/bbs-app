package xyz.majorkevin.bbs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.Post;
import xyz.majorkevin.bbs.service.PostService;

import java.util.List;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;

    @GetMapping("/")
    public String index(Model model){
        List<Post> allPosts = postService.findAllWithBriefContent();
        logger.info("get posts: " + allPosts);
        model.addAttribute("allPosts", allPosts);
        return "index";
    }

    @GetMapping("/checkpost/{postId}")
    public String getThePost(@PathVariable("postId") Long postId, Model model){
        Post thePost = postService.findById(postId);
        List<Comment> thePostComments = thePost.getComments();
        model.addAttribute("thePost", thePost);
        model.addAttribute("theComments", thePostComments);
        return "show-post";
    }
}
