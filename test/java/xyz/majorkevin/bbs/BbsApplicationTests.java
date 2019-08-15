package xyz.majorkevin.bbs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({AuthControllerTests.class, IndexControllerTests.class, PostControllerTests.class, ProfileControllerTests.class})
public class BbsApplicationTests {

    @Test
    public void contextLoads() {
    }

}
