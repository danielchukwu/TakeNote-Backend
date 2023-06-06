package com.goodcode.notebook;

import com.goodcode.note.Note;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/notebooks")
public class NotebookController {
    private final NotebookService notebookService;

    // CREATE
    @PostMapping
    public ResponseEntity<Notebook> createNotebook(@RequestBody NotebookDTO notebook, Principal principal) {
        Notebook createdNotebook = this.notebookService.createNotebook(notebook, principal);
        return ResponseEntity.ok(createdNotebook);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<Notebook> getNotebook(@PathVariable("id") UUID id, Principal principal) {
        Notebook notebook = this.notebookService.getNotebook(id, principal);
        return ResponseEntity.ok(notebook);
    }
    @GetMapping("/{id}/notes")
    public ResponseEntity<List<Note>> getNotebookNotes(@PathVariable("id") UUID id, Principal principal) {
        List<Note> notebook = this.notebookService.getNotebookNotes(id, principal);
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
            Principal principal)
    {
        Notebook updateNotebook = this.notebookService.updateNotebook(id, notebook, principal);
        return ResponseEntity.ok(updateNotebook);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotebook(@PathVariable("id") UUID id) {
        this.notebookService.deleteNotebook(id);
        return ResponseEntity.noContent().build();
    }
}
