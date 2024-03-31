package pl.strefakursow.security.simple.acl;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class DocumentRepository {

    private ConcurrentMap<UUID, Document> documents;

    public DocumentRepository() {
        documents = new ConcurrentHashMap<>();
    }

    public Document create(String title, String content, String owner) {
        UUID id = UUID.randomUUID();
        Document document = new Document(id, title, content, Map.of(owner, Set.of("READ", "DELETE"), "reader", Set.of("READ")));
        documents.put(id, document);
        return document;
    }

    public Optional<Document> findById(UUID id) {
        return Optional.ofNullable(documents.get(id));
    }

    public void delete(UUID id) {
        documents.remove(id);
    }
}
