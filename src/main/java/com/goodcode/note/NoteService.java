package com.goodcode.note;

import com.goodcode.notebook.NotebookRepository;
import com.goodcode.user.User;
import com.goodcode.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NoteService {
    final NoteRepository noteRepository;
    final UserRepository userRepository;
    final NotebookRepository notebookRepository;

    // CREATE
    public Note createNote(NoteDTO note) {
        // Grab the note owner
        var user = userRepository.findById(note.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        // Grab the notebook this note belongs to
        var notebook = notebookRepository.findById(note.getNotebookId())
                .orElseThrow(() -> new UsernameNotFoundException("notebook not found"));

        var newNote = Note.builder()
                .title(note.getTitle())
                .avatar(note.getAvatar())
                .description(note.getDescription())
                .body(note.getBody())
                .notebook(notebook)
                .user(user)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        noteRepository.save(newNote);

        return newNote;
    }

    // READ
    public Note getNote(UUID id, Principal principal) {
        return noteRepository.findById(id)
                .filter(note -> note.getUser().getEmail().equals( principal.getName() ))
                .orElseThrow(() ->  new UsernameNotFoundException("note not found"));
    }
    public List<Note> getNotes(Principal principal) {
        User user = userRepository.findByEmail( principal.getName() ).get();
        return noteRepository.findAllByUserId(user.getId());
    }

    // UPDATE
    public Note updateNote(UUID id, Note updatedNote) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        // Update - Avatar, Title, Description, Body, Updated At
        existingNote.setAvatar( updatedNote.getAvatar() != null ? updatedNote.getAvatar() : existingNote.getAvatar());
        existingNote.setTitle( updatedNote.getTitle() != null ? updatedNote.getTitle() : existingNote.getTitle());
        existingNote.setDescription( updatedNote.getDescription() != null ? updatedNote.getDescription() : existingNote.getDescription());
        existingNote.setBody( updatedNote.getBody() != null ? updatedNote.getBody() : existingNote.getBody());
        existingNote.setUpdatedAt(LocalDateTime.now());

        return noteRepository.save(existingNote);
    }

    // DELETE
    public void deleteNote(UUID id) {
        noteRepository.deleteById(id);
    }

}
