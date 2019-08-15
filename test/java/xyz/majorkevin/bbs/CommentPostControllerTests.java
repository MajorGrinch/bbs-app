package xyz.majorkevin.bbs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import xyz.majorkevin.bbs.entity.Comment;
import xyz.majorkevin.bbs.service.PostService;
import xyz.majorkevin.bbs.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CommentPostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Value("${testPostId}")
    private Long testPostId;

    private String testCommentContent = "this is a test comment";

    @Test
    public void testCommentPostUnauthenticated() throws Exception{
        MockHttpServletRequestBuilder postForm = post("/commentpost/" + testPostId)
                .param("content", testCommentContent)
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails("testExist")
    public void testCommentPostAuthenticated() throws Exception{
        MockHttpServletRequestBuilder postForm = post("/commentpost/" + testPostId)
                .param("content", testCommentContent)
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/checkpost/" + testPostId));

        List<Comment> commentList = postService.findById(testPostId).getComments();
        assertThat(commentList.size()).isNotEqualTo(0);
        assertThat(commentList.get(0).getContent()).isEqualTo(testCommentContent);

    }
}
