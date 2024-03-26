package pl.strefakursow.security.attacks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.strefakursow.security.attacks.sqlinjection.User;
import pl.strefakursow.security.attacks.sqlinjection.UserDao;

public class SqlInjectionTests {
    private UserDao userDao;

    @DisplayName(
            "given 4 users, " +
                    "when call unsafe query method using SQL injection attack, " +
                    "then all 4 users are returned"
    )
    // @formatter:on
    @Test
    @WithMockUser
    void unsafe() throws Exception {
        // given
        userDao.save(new User("goobar"),new User("foobar"),
                new User("hoobar"),new User("goobar"));
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/secured-basic"))
                // then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
