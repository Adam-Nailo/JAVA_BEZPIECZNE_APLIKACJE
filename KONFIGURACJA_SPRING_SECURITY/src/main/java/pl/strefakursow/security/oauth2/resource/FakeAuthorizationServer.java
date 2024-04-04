package pl.strefakursow.security.oauth2.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FakeAuthorizationServer {
    @GetMapping("/oauth/jwkset")
    public Map<String, Object> jwkSet() {
        // @formatter:off
        return Map.of(
                "keys", List.of(
                        Map.of(
                                "kty", "RSA",
                                "e", "AQAB",
                                "kid", "28966cb6-0865-4e81-8369-4387367d0a9d",
                                "n","fqfKxw7BSSDvtxPvEFaN136kjYfK9dqKsJUAyrDlgnU_3FRD95Fa9u5uOJ6tjK4ykYId9hkjyDIziZ6TRQlteeetU-6LA5DsA8lKWNtVgZ_E30LGAPtT__AiSbmtUKbzn9KJATkjpcPQzORsNXwmhy4QIXCeVi0W7AIRMFhbv7M"
                        )));
        // @formatter:on
    }
}