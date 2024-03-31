package pl.strefakursow.security.simple.acl;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.UUID;

@Component
public class DocumentPermissionEvaluator implements PermissionEvaluator {

    private DocumentRepository documentRepository;

    public DocumentPermissionEvaluator(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        UUID id = (UUID) targetId;
        return documentRepository.findById(id).
                map(document -> document.getPermissions()
                        .getOrDefault(authentication.getName(), Collections.emptySet()).contains(permission)).orElse(false);
    }
}
