package pl.strefakursow.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    // @formatter:off
    @DisplayName(
            "given no credentials, " +
                    "when GET on /secured-basic" +
                    "then status is unauthorized"
    )
    // @formatter:on
    @Test
    void basicAuthNegative() throws Exception {
        // given

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/secured-basic"))
        // then
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    // @formatter:off
    @DisplayName(
            "given user:user basic auth credentials, " +
                    "when GET on /secured-basic" +
                    "then status is OK"
    )
    // @formatter:on
    @Test
    void basicAuthPositive() throws Exception {
        // given

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/secured-basic").with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user")))
                // then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    // @formatter:off
    @DisplayName(
            "given mock user" +
                    "when GET on /secured-basic" +
                    "then status is OK"
    )
    // @formatter:on
    @Test
    @WithMockUser
    void mockUser() throws Exception {
        // given

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/secured-basic"))
                // then
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
