package com.goodcode.notebook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotebookService {

    final NotebookRepository notebookRepository;

    @Autowired
    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
        this.notebookRepository.saveAll(defaultNotebooks());
    }

    public static List<Notebook> defaultNotebooks() {
        return List.of(
                new Notebook(UUID.randomUUID(), null, "Angular", "This are key notes on the technology angular I've been learning for some time now and I absolutely love the technology but in some ways react actually beats angular."),
                new Notebook(UUID.randomUUID(), null, "Learning Javascript", "This are key notes on the technology angular I've been learning for some time now and I absolutely love the technology but in some ways react actually beats angular."),
                new Notebook(UUID.randomUUID(), null, "Goals in Life", "This are key notes on the technology angular I've been learning for some time now and I absolutely love the technology but in some ways react actually beats angular."),
                new Notebook(UUID.randomUUID(), null, "Bible Scriptures", "This are key notes on the technology angular I've been learning for some time now and I absolutely love the technology but in some ways react actually beats angular.")
        );
    }

    // CREATE
    public Notebook createNotebook(Notebook notebook) {
        return this.notebookRepository.save(notebook);
    }

    // READ
    public Optional<Notebook> getNotebook(UUID id) { return this.notebookRepository.findById(id); }
    public List<Notebook> getNotebooks() { return this.notebookRepository.findAll(); }

    // UPDATE
    public Notebook updateNotebook(UUID id, Notebook updatedNotebook) {
        Notebook existingNotebook = this.notebookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        // Update - Avatar, Title, Description, Updated At
        existingNotebook.setAvatar( updatedNotebook.getAvatar() != null ? updatedNotebook.getAvatar() : existingNotebook.getAvatar());
        existingNotebook.setTitle( updatedNotebook.getTitle() != null ? updatedNotebook.getTitle() : existingNotebook.getTitle());
        existingNotebook.setDescription( updatedNotebook.getDescription() != null ? updatedNotebook.getDescription() : existingNotebook.getDescription());
        existingNotebook.setUpdatedAt(LocalDateTime.now());

        return this.notebookRepository.save(existingNotebook);
    }

    // DELETE
    public void deleteNotebook(UUID id) {
        this.notebookRepository.deleteById(id);
    }

}
