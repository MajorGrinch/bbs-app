package xyz.majorkevin.bbs.form;

import lombok.Data;
import xyz.majorkevin.bbs.entity.Post;
import xyz.majorkevin.bbs.entity.User;

import java.util.Date;

@Data
public class ArticleForm {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ArticleForm.class);

    private String title;

    private String content;

    public Post toPost(User user, Date postTime){
        logger.info(title);
        logger.info(content);
        logger.info("user: " + user);
        logger.info("post time: " + postTime);
        Post thisPost = new Post(title, content, postTime);
        thisPost.setUser(user);
        return thisPost;
    }
}
