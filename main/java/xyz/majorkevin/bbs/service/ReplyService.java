package xyz.majorkevin.bbs.service;

import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> findByComment(Comment theComment);

    void save(Reply theReply);
}
