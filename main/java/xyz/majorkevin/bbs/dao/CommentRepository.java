package xyz.majorkevin.bbs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.majorkevin.bbs.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
