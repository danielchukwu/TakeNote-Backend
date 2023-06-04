package com.goodcode.notebook;

import com.goodcode.note.Note;
import com.goodcode.note.NoteRepository;
import com.goodcode.user.User;
import com.goodcode.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotebookService {

    final NotebookRepository notebookRepository;
    final NoteRepository noteRepository;
    final UserRepository userRepository;

    // CREATE
    public Notebook createNotebook(NotebookDTO notebook) {
        // Grab the note owner
        var user = userRepository.findById(notebook.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        var newNotebook = Notebook.builder()
                .title(notebook.getTitle())
                .avatar(notebook.getAvatar())
                .description(notebook.getDescription())
                .user(user)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        notebookRepository.save(newNotebook);

        return newNotebook;
    }

    // READ
    public Notebook getNotebook(UUID id, Principal principal) {
        // Getting a single notebook
        return notebookRepository.findById(id)
                .filter(notebook -> notebook.getUser().getEmail().equals( principal.getName() ))
                .orElseThrow(() -> new IllegalArgumentException("note not found"));
    }
    public List<Note> getNotebookNotes(UUID id, Principal principal) {
        // Getting a List of notes for a notebook
        return noteRepository.findByNotebookId(id);
    }
    public List<Notebook> getNotebooks(Principal principal) {
        // Getting a list of notebooks
        User user = userRepository.findByEmail(principal.getName()).get();
        return notebookRepository.findByUserId(user.getId());
    }

    // UPDATE
    public Notebook updateNotebook(UUID id, Notebook updatedNotebook) {
        Notebook existingNotebook = notebookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        // Update - Avatar, Title, Description, Updated At
        existingNotebook.setAvatar( updatedNotebook.getAvatar() != null ? updatedNotebook.getAvatar() : existingNotebook.getAvatar());
        existingNotebook.setTitle( updatedNotebook.getTitle() != null ? updatedNotebook.getTitle() : existingNotebook.getTitle());
        existingNotebook.setDescription( updatedNotebook.getDescription() != null ? updatedNotebook.getDescription() : existingNotebook.getDescription());
        existingNotebook.setUpdatedAt(LocalDateTime.now());

        return notebookRepository.save(existingNotebook);
    }

    // DELETE
    public void deleteNotebook(UUID id) {
        notebookRepository.deleteById(id);
    }
}
