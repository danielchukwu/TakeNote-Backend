package com.goodcode.notebook;

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
public class NotebookService {

    final NotebookRepository notebookRepository;
    final UserRepository userRepository;

    // CREATE
    public Notebook createNotebook(Notebook notebook, Principal principal) {
        // Grab the note owner
        var user = userRepository.findByEmail(principal.getName())
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
        return notebookRepository.findById(id)
                .filter(notebook1 -> notebook1.getUser().getEmail().equals( principal.getName() ))
                .orElseThrow(() -> new IllegalArgumentException("not found"));
    }
    public List<Notebook> getNotebooks(Principal principal) {
        User user = userRepository.findByEmail(principal.getName()).get();
        return notebookRepository.findAllByUserId(user.getId());
    }

    // UPDATE
    public Notebook updateNotebook(UUID id, Notebook updatedNotebook, Principal principal) {
        Notebook existingNotebook = getNotebook(id, principal);

        // Update - Avatar, Title, Description, Updated At
        existingNotebook.setAvatar( updatedNotebook.getAvatar() != null ? updatedNotebook.getAvatar() : existingNotebook.getAvatar());
        existingNotebook.setTitle( updatedNotebook.getTitle() != null ? updatedNotebook.getTitle() : existingNotebook.getTitle());
        existingNotebook.setDescription( updatedNotebook.getDescription() != null ? updatedNotebook.getDescription() : existingNotebook.getDescription());
        existingNotebook.setUpdatedAt(LocalDateTime.now());

        return notebookRepository.save(existingNotebook);
    }

    // DELETE
    public void deleteNotebook(UUID id, Principal principal) {
        Notebook notebook = getNotebook(id, principal);
        notebookRepository.delete(notebook);
    }

}
