package pl.strefakursow.security.oauth2.resource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceServerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    // @formatter:off
    @DisplayName(
            "given no jwt token, " +
                    "when GET on /api/messages, " +
                    "then status is unauthorized"
    )
    // @formatter:on
    @Test
    void testNoToken() throws Exception {
        // when
        mockMvc.perform(get("/api/messages"))

                // then
                .andExpect(status().isUnauthorized());
    }

    // @formatter:off
    @DisplayName(
            "given jwt token with message:noread scope, " +
                    "when GET on /api/messages, " +
                    "then status is forbidden"
    )
    // @formatter:on
    @Test
    void testNoReadToken() throws Exception {
        // when
        mockMvc.perform(get("/api/messages").with(jwt(
                        builder -> builder.claim("scope", "message:noread"))))

                // then
                .andExpect(status().isForbidden());
    }

    // @formatter:off
    @DisplayName(
            "given jwt token with message:read scope, " +
                    "when GET on /api/messages, " +
                    "then status is OK"
    )
    // @formatter:on
    @Test
    void testReadToken() throws Exception {
        // when
        mockMvc.perform(get("/api/messages").with(jwt(
                        builder -> builder.claim("scope", "message:read"))))

                // then
                .andExpect(status().isOk());
    }
}