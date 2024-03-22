package pl.strefakursow.security.acl;

import lombok.Value;

@Value
public class CreateDocumentDto {
    private String title;
    private String content;
}
