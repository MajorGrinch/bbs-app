package xyz.majorkevin.bbs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.UpdateUserProfileForm;
import xyz.majorkevin.bbs.service.UserService;

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
public class ProfileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ProfileControllerTests.class);

    @Test
    public void testShowProfileUnAuthorized() throws Exception{
        mockMvc.perform(get("/profile/manage"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithUserDetails("testExist")
    public void testShowProfilePageAuthorized() throws Exception{
        mockMvc.perform(get("/profile/manage"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("testExist")
    public void testUpdateProfileWithValidInput() throws Exception{

        UpdateUserProfileForm form = new UpdateUserProfileForm();
        form.setUsername("testExist");
        form.setEmail("another@email.com");

        MockHttpServletRequestBuilder postForm = post("/profile/manage")
                .param("username", form.getUsername())
                .param("email", form.getEmail())
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"));

        User user = userService.findById((long)24);
        logger.info("user: " + user);//        assertEquals(user.getEmail(), "another@email.com");

        assertThat(user.getEmail()).isEqualTo("another@email.com");
    }

    @Test
    @WithUserDetails("testExist")
    public void testUpdateProfileWithBlankUsername() throws Exception{
        UpdateUserProfileForm form = new UpdateUserProfileForm();
        form.setUsername("  ");
        form.setEmail("another@email.com");

        MockHttpServletRequestBuilder postForm = post("/profile/manage")
                .param("username", form.getUsername())
                .param("email", form.getEmail())
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(content().string(containsString("Username can not be blank")));
    }

    @Test
    @WithUserDetails("testExist")
    public void testUpdateProfileWithBlankEmail() throws Exception{
        UpdateUserProfileForm form = new UpdateUserProfileForm();
        form.setUsername("testExist");
        form.setEmail("   ");

        MockHttpServletRequestBuilder postForm = post("/profile/manage")
                .param("username", form.getUsername())
                .param("email", form.getEmail())
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().isOk())
                .andExpect(view().name("user-profile"))
                .andExpect(content().string(containsString("Email can not be blank")));
    }
}
