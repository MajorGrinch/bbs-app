package xyz.majorkevin.bbs.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import xyz.majorkevin.bbs.entity.MyUserDetail;
import xyz.majorkevin.bbs.entity.Post;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.ArticleForm;
import xyz.majorkevin.bbs.service.PostService;

import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostService postService;

    @GetMapping("/write")
    public String showMakePostPage(Model model) {
        model.addAttribute("articleForm", new ArticleForm());
        return "make-post";
    }

    @PostMapping("/write")
    public String processArticleForm(ArticleForm articleForm, Errors errors, @AuthenticationPrincipal MyUserDetail userDetail) {
        if (errors.hasErrors()) {
            return "make-post";
        }

        User currUser = userDetail.getUser();

        logger.info("use annotation");
        logger.info("user " + (currUser == null ? "==" : "!=") + " null");

        Post thisPost = articleForm.toPost(currUser, new Date());
        postService.save(thisPost);
        return "redirect:/";
    }

    @GetMapping("/mine")
    public String getMyPosts(Model model, @AuthenticationPrincipal MyUserDetail userDetail) {
        List<Post> allMyPosts = postService.findByUserWithBriefContent(userDetail.getUser());
        logger.info("my posts: " + allMyPosts);
        model.addAttribute("allMyPosts", allMyPosts);
        return "my-posts";
    }

    @GetMapping("/delete/{postId}")
    public String deleteThePost(@PathVariable("postId") Long postId, @AuthenticationPrincipal MyUserDetail userDetail, Model model) {

        Post thisPost = postService.findById(postId);

        if(thisPost == null){
            return "redirect:/post/mine";
        }

        if(!thisPost.getUser().getId().equals(userDetail.getUser().getId())){
            logger.info("You can't delete other's post");
            return "redirect:/post/mine";
        }

        postService.deleteById(postId);

        List<Post> allMyPosts = postService.findByUserWithBriefContent(userDetail.getUser());
        logger.info("my posts: " + allMyPosts);
        model.addAttribute("allMyPosts", allMyPosts);

        return "redirect:/post/mine";
    }
}
