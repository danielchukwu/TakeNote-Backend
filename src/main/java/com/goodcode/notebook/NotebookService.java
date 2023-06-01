package com.goodcode.notebook;

import com.goodcode.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class NotebookService {

    final NotebookRepository notebookRepository;
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
    public Optional<Notebook> getNotebook(UUID id) { return notebookRepository.findById(id); }
    public List<Notebook> getNotebooks() { return notebookRepository.findAll(); }

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
