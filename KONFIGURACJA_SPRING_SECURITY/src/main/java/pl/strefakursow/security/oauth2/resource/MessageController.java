package pl.strefakursow.security.oauth2.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.stream.Stream;

@RestController
public class MessageController {
    @GetMapping("/api/messages")
    public Stream<Map<String, Object>> getMessages() {
        // @formatter:off
        return Stream.of(
                Map.of(
                        "title", "First Message",
                        "content","First message content"),
                Map.of(
                        "title", "Second Message",
                        "content","Second message content")
        );
        // @formatter:on
    }
}
