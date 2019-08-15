package xyz.majorkevin.bbs.form;

import lombok.Data;
import xyz.majorkevin.bbs.entity.Comment;

import java.util.Date;

@Data
public class CommentForm {
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment toComment(int agreeAmount, int opposeAmount){
        return new Comment(content, 0, 0);
    }
}
