package xyz.majorkevin.bbs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    public List<Reply> findByComment(Comment theComment);
}
