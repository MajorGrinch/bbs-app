package xyz.majorkevin.bbs.form;

import javax.validation.constraints.NotNull;

public class VoteComment {

    @NotNull(message = "comment id can not be null")
    private Long commentId;

    @NotNull(message = "action can not be null")
    private Integer actionCode;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Integer getActionCode() {
        return actionCode;
    }

    public void setActionCode(Integer actionCode) {
        this.actionCode = actionCode;
    }

    @Override
    public String toString() {
        return "VoteComment{" +
                "commentId=" + commentId +
                ", actionCode=" + actionCode +
                '}';
    }
}
