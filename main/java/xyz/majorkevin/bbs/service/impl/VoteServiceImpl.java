package xyz.majorkevin.bbs.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.entity.UserVote;
import xyz.majorkevin.bbs.service.VoteService;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class VoteServiceImpl implements VoteService {

    private MongoOperations mongoOperations;

    @Autowired
    public VoteServiceImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    private static final Log log = LogFactory.getLog(VoteServiceImpl.class);

    @Override
    public void upvote(String userVoteId, Long commentId) {
        mongoOperations.updateFirst(query(where("id").is(userVoteId)), new Update().addToSet("upvotes", commentId), UserVote.class);
    }

    @Override
    public void downvote(String userVoteId, Long commentId) {
        mongoOperations.updateFirst(query(where("id").is(userVoteId)), new Update().addToSet("downvotes", commentId), UserVote.class);
    }

    @Override
    public String insertUserVoteData(String username) {
        UserVote userVote = new UserVote(username, new Long[0], new Long[0]);
        mongoOperations.insert(userVote);
        return  userVote.getId();
    }
}
