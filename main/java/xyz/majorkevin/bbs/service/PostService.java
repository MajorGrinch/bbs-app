package xyz.majorkevin.bbs.service;

import xyz.majorkevin.bbs.entity.Post;
import xyz.majorkevin.bbs.entity.User;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    List<Post> findAllWithBriefContent();

    void save(Post post);

    Post findByTitle(String test_title);

    List<Post> findByUser(User user);

    List<Post> findByUserWithBriefContent(User user);

    Post findById(Long postId);

    void deleteById(Long postId);
}
