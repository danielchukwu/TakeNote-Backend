package com.goodcode.notebook;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/notebooks")
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Notebook> createNotebook(@RequestBody Notebook notebook, Principal principal) {
        Notebook createdNotebook = this.notebookService.createNotebook(notebook, principal);
        return ResponseEntity.ok(createdNotebook);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<Notebook> getNotebook(@PathVariable("id") UUID id, Principal principal) {
        Notebook notebook = this.notebookService.getNotebook(id, principal);
        return ResponseEntity.ok(notebook);
    }

    @GetMapping
    public ResponseEntity<List<Notebook>> getNotebooks(Principal principal) {
        var notebookList = this.notebookService.getNotebooks(principal);
        return ResponseEntity.ok(notebookList);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Notebook> updateNotebook(
            @PathVariable("id") UUID id,
            @RequestBody Notebook notebook,
            Principal principal
    )
=======
            Principal principal)
    {
        Notebook updateNotebook = this.notebookService.updateNotebook(id, notebook, principal);
        return ResponseEntity.ok(updateNotebook);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotebook(@PathVariable("id") UUID id, Principal principal) {
        this.notebookService.deleteNotebook(id, principal);
        return ResponseEntity.noContent().build();
    }
}
