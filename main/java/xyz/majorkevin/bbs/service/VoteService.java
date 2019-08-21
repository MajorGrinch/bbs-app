package xyz.majorkevin.bbs.service;

import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.entity.UserVote;

import java.util.List;

public interface VoteService {
    void upvote(String userVoteId, Long commentId);

    void downvote(String userVoteId, Long commentId);

    String insertUserVoteData(String username);

    UserVote getUserVoteData(User user);
}
