package com.example.ecommercebooksales.controller;

import com.example.ecommercebooksales.dto.PublisherDTO;
import com.example.ecommercebooksales.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherService publisherService;
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublisherDTO> createAuthor(@RequestBody PublisherDTO request) {
        PublisherDTO createPublisher = publisherService.createPublisher(request);
        return ResponseEntity.ok(createPublisher);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<List<PublisherDTO>> getAllPublishers() {
        return ResponseEntity.ok(publisherService.getAllPublisher());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<PublisherDTO> getPublisherById(@PathVariable Long id) {
        PublisherDTO publisher = publisherService.getPublisherById(id);
        if (publisher == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(publisher);
    }

    // ---------------- UPDATE ----------------
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublisherDTO> updateAuthors(@PathVariable Long id,
                                                   @RequestBody PublisherDTO request) {
        PublisherDTO updatedPublisher = publisherService.updatePublisher(id, request);
        if (updatedPublisher == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedPublisher);
    }

    // ---------------- DELETE ----------------
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthors(@PathVariable Long id) {
        boolean deleted = publisherService.deletePublisher(id);
        if (!deleted) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build(); // 204
    }
}
