package xyz.majorkevin.bbs;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.form.RegistrationForm;
import xyz.majorkevin.bbs.service.UserService;


import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTests {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void showLoginPage() throws Exception{
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(content().string(containsString("Password")));
    }

    @Test
    public void loginUser() throws Exception{
        MockHttpServletRequestBuilder postForm = post("/auth/authenticateUser")
                .param("username", "testExist")
                .param("password", "1234")
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void showRegisterPage() throws Exception{
        mockMvc.perform(get("/auth/registerUser"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(content().string(containsString("Confirm password:")));
    }

    @Test
    public void createUser() throws Exception {
        MockHttpServletRequestBuilder postForm = post("/auth/registerUser")
                                                    .param("username", "testNotExist")
                                                    .param("password", "1234")
                                                    .param("confirm_password", "1234")
                                                    .param("email", "testnotexist@email.com")
                                                    .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        User user = userService.findByUsername("testNotExist");
        assertThat(user).isNotEqualTo(null);
    }

    @Test
    public void createDuplicatedUser() throws Exception {
        MockHttpServletRequestBuilder postForm = post("/auth/registerUser")
                .param("username", "testExist")
                .param("password", "1234")
                .param("confirm_password", "1234")
                .param("email", "testexist@email.com")
                .with(csrf());

        mockMvc.perform(postForm)
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(content().string(containsString("User already exists")));
    }
}
