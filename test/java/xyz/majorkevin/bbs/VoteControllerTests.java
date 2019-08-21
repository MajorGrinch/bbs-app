package xyz.majorkevin.bbs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import xyz.majorkevin.bbs.entity.UserVote;
import xyz.majorkevin.bbs.form.VoteComment;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class VoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(VoteControllerTests.class);

    @Test
    @WithUserDetails("testAccount")
    public void testVoteComment() throws Exception{
        VoteComment voteComment = new VoteComment();
        voteComment.setActionCode(1);
        voteComment.setCommentId((long)7);
        MockHttpServletRequestBuilder upvoteComment = post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(voteComment))
                .with(csrf());

        mockMvc.perform(upvoteComment)
                .andExpect(status().isOk());

        UserVote userVote = mongoOperations.findOne(query(where("username").is("testAccount")), UserVote.class);
        assertThat(userVote).isNotNull();
        List<Long> upvotes = Arrays.asList(userVote.getUpvotes());
        assertThat(upvotes.contains((long)7)).isTrue();
    }
}
