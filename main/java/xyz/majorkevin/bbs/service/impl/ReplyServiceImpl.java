package xyz.majorkevin.bbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.majorkevin.bbs.dao.ReplyRepository;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.entity.Reply;
import xyz.majorkevin.bbs.service.ReplyService;

import java.util.List;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Override
    public List<Reply> findByComment(Comment theComment) {
        return replyRepository.findByComment(theComment);
    }

    @Override
    public void save(Reply theReply) {
        replyRepository.save(theReply);
    }
}
