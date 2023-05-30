package com.goodcode.note;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Note {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "avatar", nullable = true, updatable = true)
    private String avatar;
    @Column(name = "title", nullable = true, updatable = true)
    private String title;
    @Column(name = "description", length = 2000, nullable = true, updatable = true)
    private String description;
    @Column(name = "body", length = 20_000, nullable = true, updatable = true)
    private String body;
    @Column(name = "notebook_id", nullable = true, updatable = false)
    private UUID notebookId;
    @Column(name = "upated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Note() {
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    public Note(UUID id, String avatar, String title, String description, String body, UUID notebookId, LocalDateTime updatedAt, LocalDateTime createdAt) {
        this.id = id;
        this.avatar = avatar;
        this.title = title;
        this.description = description;
        this.body = body;
        this.notebookId = notebookId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Note(UUID id, String avatar, String title, String description, String body, UUID notebookId) {
        this.id = id;
        this.avatar = avatar;
        this.title = title;
        this.description = description;
        this.body = body;
        this.notebookId = notebookId;
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }


    // GETTERS AND SETTERS
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public UUID getNotebookId() { return notebookId; }
    public void setNotebookId(UUID userId) { this.notebookId = userId; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(avatar, note.avatar) && Objects.equals(title, note.title) && Objects.equals(description, note.description) && Objects.equals(updatedAt, note.updatedAt) && Objects.equals(createdAt, note.createdAt);
    }

    @Override
    public int hashCode() { return Objects.hash(id, avatar, title, description, updatedAt, createdAt); }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
