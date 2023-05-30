package com.goodcode.notebook;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Notebook {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "avatar", nullable = true, updatable = true)
    private String avatar;
    @Column(name = "title", nullable = false, updatable = true)
    private String title;
    @Column(name = "description", length = 2000, nullable = false, updatable = true)
    private String description;
    @Column(name = "user_id", nullable = true, updatable = true)
    private UUID userId;
    @Column(name = "upated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Notebook() {
        this.updatedAt = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

    public Notebook(UUID id, String avatar, String title, String description) {
        this.id = id;
        this.avatar = avatar;
        this.title = title;
        // this.userId = "";
        this.description = description;
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

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // EQUALS & HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notebook notebook = (Notebook) o;
        return Objects.equals(id, notebook.id) && Objects.equals(avatar, notebook.avatar) && Objects.equals(title, notebook.title) && Objects.equals(description, notebook.description) && Objects.equals(updatedAt, notebook.updatedAt) && Objects.equals(createdAt, notebook.createdAt);
    }

    @Override
    public int hashCode() { return Objects.hash(id, avatar, title, description, updatedAt, createdAt); }

    // toString

    @Override
    public String toString() {
        return "Notebook{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}
