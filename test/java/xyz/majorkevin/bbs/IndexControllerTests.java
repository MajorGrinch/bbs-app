package xyz.majorkevin.bbs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IndexControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Value("${testPostId}")
    private Long testPostId;

    @Value("${testPostContent}")
    private String testPostContent;

    @Test
    public void testIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Kevin BBS")));
    }

    @Test
    public void testCheckPostPage() throws Exception {
        mockMvc.perform(get("/checkpost/" + testPostId))
                .andExpect(status().isOk())
                .andExpect(view().name("show-post"))
                .andExpect(content().string(containsString(testPostContent)));
    }
}
