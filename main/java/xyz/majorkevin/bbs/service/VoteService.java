package xyz.majorkevin.bbs.service;

import xyz.majorkevin.bbs.entity.Comment;

public interface VoteService {
    void upvote(String userVoteId, Long commentId);

    void downvote(String userVoteId, Long commentId);

    String insertUserVoteData(String username);
}
