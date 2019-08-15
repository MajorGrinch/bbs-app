package xyz.majorkevin.bbs.service;

import xyz.majorkevin.bbs.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();

    void save(Comment comment);
}
