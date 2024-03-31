package pl.strefakursow.security.simple.acl;

import lombok.Value;

@Value
public class CreateDocumentDto {
    private String title;
    private String content;
}
