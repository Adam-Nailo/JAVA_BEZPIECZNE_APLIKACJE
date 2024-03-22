package pl.strefakursow.security.acl;

import lombok.Value;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Value
public class Document {
    private UUID id;
    private String title;
    private String content;
    private Map<String, Set<String>> permissions;
}
