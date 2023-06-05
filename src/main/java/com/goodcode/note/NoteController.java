package com.goodcode.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {
    final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    // CREATE
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody NoteDTO note) {
        Note createdNote = noteService.createNote(note);
        return ResponseEntity.ok(createdNote);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<Note> getNote(@PathVariable("id") UUID id, Principal principal) {
        Note note = noteService.getNote(id, principal);
        return ResponseEntity.ok(note);
    }
    @GetMapping
    public ResponseEntity<List<Note>> getNotes(Principal principal) {
        var noteList = noteService.getNotes(principal);
        noteList.sort(Comparator.comparing(Note::getUpdatedAt).reversed());
        return ResponseEntity.ok(noteList);
    }


    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(
            @PathVariable("id") UUID id,
            @RequestBody Note note)
    {
        Note updateNote = this.noteService.updateNote(id, note);
        return ResponseEntity.ok(updateNote);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") UUID id) {
        this.noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

}
