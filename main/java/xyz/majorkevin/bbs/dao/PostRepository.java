package xyz.majorkevin.bbs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import xyz.majorkevin.bbs.entity.Post;
import xyz.majorkevin.bbs.entity.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);

    List<Post> findByUser(User user);
}
