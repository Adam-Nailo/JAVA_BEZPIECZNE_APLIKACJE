package pl.strefakursow.security.simple.acl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private DocumentRepository documentRepository;

    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @PostMapping
    public Document create(@RequestBody CreateDocumentDto dto, Principal principal) {
        return documentRepository.create(dto.getTitle(), dto.getContent(), principal.getName());
    }

    @PreAuthorize("hasPermission(#id,'pl.strefakursow.security.acl.Document', 'READ')")
    @GetMapping("/{id}")
    public Optional<Document> find(@PathVariable("id") UUID id) {
        return documentRepository.findById(id);
    }
    @PreAuthorize("hasPermission(#id,'pl.strefakursow.security.acl.Document', 'DELETE')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") UUID id){
        documentRepository.delete(id);
    }
}
