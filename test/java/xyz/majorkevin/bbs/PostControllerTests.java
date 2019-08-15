package xyz.majorkevin.bbs;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import xyz.majorkevin.bbs.entity.Post;
import xyz.majorkevin.bbs.service.PostService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;


    @Test
    public void showMakePostPageUnauthorized() throws Exception{
        mockMvc.perform(get("/post/write"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/auth/login"));
    }

    @Test
    @WithUserDetails("testExist")
    public void showMakePostPageAuthorized() throws Exception{
        mockMvc.perform(get("/post/write"))
                .andExpect(status().isOk())
                .andExpect(view().name("make-post"))
                .andExpect(content().string(containsString("Write a new post")));
    }

    @Test
    public void createThePostUnauthorized() throws Exception{
        MockHttpServletRequestBuilder postForm = post("/post/write")
                .param("title", "test title")
                .param("content", "test content")
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails("testExist")
    public void createThePostAuthorized() throws Exception{
        MockHttpServletRequestBuilder postForm = post("/post/write")
                .param("title", "test title")
                .param("content", "test content")
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Post thisPost = postService.findByTitle("test title");
        assertThat(thisPost).isNotEqualTo(null);
    }

    @Test
    @WithUserDetails("testExist")
    public void deleteMyPostAuthorized() throws Exception{


        mockMvc.perform(get("/post/delete/7"))
                .andExpect(status().isOk());

        Post deletedPost = postService.findById((long)7);
        assertThat(deletedPost).isEqualTo(null);
    }

    @Test
    @WithUserDetails("testExist")
    public void deletedOthersPostAuthorized() throws Exception{
        mockMvc.perform(get("/post/delete/12"));

        Post othersPost = postService.findById((long)12);
        assertThat(othersPost).isNotEqualTo(null);
    }

}
