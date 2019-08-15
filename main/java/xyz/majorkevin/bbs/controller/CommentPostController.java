package xyz.majorkevin.bbs.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.MyUserDetail;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.CommentForm;
import xyz.majorkevin.bbs.service.CommentService;
import xyz.majorkevin.bbs.service.PostService;

import java.util.Date;


@Slf4j
@Controller
@RequestMapping("/commentpost")
public class CommentPostController {

    private static final Logger logger = LoggerFactory.getLogger(CommentPostController.class);

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @PostMapping("/{postId}")
    public String processSubmittedComment(@PathVariable Long postId, CommentForm commentForm, @AuthenticationPrincipal MyUserDetail userDetail){
        logger.info(commentForm.toString());
        User user = userDetail.getUser();
        Comment theComment = commentForm.toComment(0, 0);
        theComment.setCommentTime(new Date());
        theComment.setUser(user);
        theComment.setPost(postService.findById(postId));

        commentService.save(theComment);
        return "redirect:/checkpost/" + postId;
    }

}
