package com.goodcode.notebook;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notebooks")
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Notebook> createNotebook(@RequestBody NotebookDTO notebook) {
        Notebook createdNotebook = this.notebookService.createNotebook(notebook);
        return ResponseEntity.ok(createdNotebook);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<Notebook> getNotebook(@PathVariable("id") UUID id) {
        Optional<Notebook> fetchedNote = this.notebookService.getNotebook(id);

        if (fetchedNote.isPresent()){
            Notebook notebook = fetchedNote.get();
            return ResponseEntity.ok(notebook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Notebook>> getNotebooks() {
        var notebookList = this.notebookService.getNotebooks();
        return ResponseEntity.ok(notebookList);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Notebook> updateNotebook(
            @PathVariable("id") UUID id,
            @RequestBody Notebook notebook)
    {
        Notebook updateNotebook = this.notebookService.updateNotebook(id, notebook);
        return ResponseEntity.ok(updateNotebook);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotebook(@PathVariable("id") UUID id) {
        this.notebookService.deleteNotebook(id);
        return ResponseEntity.noContent().build();
    }
}
