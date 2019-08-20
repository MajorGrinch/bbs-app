package xyz.majorkevin.bbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.majorkevin.bbs.dao.CommentRepository;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.service.CommentService;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        Optional<Comment> result = commentRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }
}
