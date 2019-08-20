package xyz.majorkevin.bbs.entity;

import java.util.Arrays;

public class UserVote {

    private String id;

    private String username;

    private Long[] upvotes;

    private Long[] downvotes;

    public UserVote(String username, Long[] upvotes, Long[] downvotes) {
        this.username = username;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Long[] getUpvotes() {
        return upvotes;
    }

    public Long[] getDownvotes() {
        return downvotes;
    }
}
