package com.goodcode.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService {
    final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

//    public static List<Note> defaultNotes() {
//        return List.of(
//                new Note(UUID.randomUUID(), "", ),
//                new Note(UUID.randomUUID(), null, "Learning Javascript", "This are key notes on the technology angular I've been learning for some time now and I absolutely love the technology but in some ways react actually beats angular."),
//                new Note(UUID.randomUUID(), null, "Goals in Life", "This are key notes on the technology angular I've been learning for some time now and I absolutely love the technology but in some ways react actually beats angular."),
//                new Note(UUID.randomUUID(), null, "Bible Scriptures", "This are key notes on the technology angular I've been learning for some time now and I absolutely love the technology but in some ways react actually beats angular.")
//        );
//    }

    // CREATE
    public Note createNote(Note notebook) {
        return this.noteRepository.save(notebook);
    }

    // READ
    public Optional<Note> getNote(UUID id) { return this.noteRepository.findById(id); }
    public List<Note> getNotes() { return this.noteRepository.findAll(); }

    // UPDATE
    public Note updateNote(UUID id, Note updatedNote) {
        Note existingNote = this.noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        // Update - Avatar, Title, Description, Body, Updated At
        existingNote.setAvatar( updatedNote.getAvatar() != null ? updatedNote.getAvatar() : existingNote.getAvatar());
        existingNote.setTitle( updatedNote.getTitle() != null ? updatedNote.getTitle() : existingNote.getTitle());
        existingNote.setDescription( updatedNote.getDescription() != null ? updatedNote.getDescription() : existingNote.getDescription());
        existingNote.setBody( updatedNote.getBody() != null ? updatedNote.getBody() : existingNote.getBody());
        existingNote.setUpdatedAt(LocalDateTime.now());

        return this.noteRepository.save(existingNote);
    }

    // DELETE
    public void deleteNote(UUID id) {
        this.noteRepository.deleteById(id);
    }

}
