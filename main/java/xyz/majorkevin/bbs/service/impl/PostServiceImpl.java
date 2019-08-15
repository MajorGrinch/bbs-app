package xyz.majorkevin.bbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import xyz.majorkevin.bbs.dao.PostRepository;
import xyz.majorkevin.bbs.entity.Post;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.service.PostService;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findAllWithBriefContent() {
        List<Post> allPosts = postRepository.findAll();
        for(Post post : allPosts){
            cutPostContent(post);
        }
        return allPosts;
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post findByTitle(String title) {
        return postRepository.findByTitle(title);
    }

    @Override
    public List<Post> findByUser(User user) {
        return postRepository.findByUser(user);
    }

    @Override
    public Post findById(Long postId) {
        Optional<Post> result = postRepository.findById(postId);
        Post thePost = null;
        if(result.isPresent()){
            thePost = result.get();
        }
        return thePost;
    }

    @Override
    public List<Post> findByUserWithBriefContent(User user) {
        List<Post> allMyPosts = postRepository.findByUser(user);
        for(Post post : allMyPosts){
            cutPostContent(post);
        }
        return allMyPosts;
    }


    private void cutPostContent(Post post){
        int originalLength = post.getContent().length();
        post.setContent(post.getContent().substring(0, Math.min(200, originalLength)));
    }

    @Override
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }
}
