package xyz.majorkevin.bbs.form;

import lombok.Data;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.Reply;
import xyz.majorkevin.bbs.entity.User;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class ReplyCommentForm {

    @NotBlank(message = "must choose a comment id")
    private Long commentId;

    @NotBlank(message = "reply cannot be empty")
    private String replyContent;

    public Reply toReply(Comment comment, User fromUser, User toUser, Date replyTime){
        return new Reply(replyContent, replyTime, comment, fromUser, toUser);
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
