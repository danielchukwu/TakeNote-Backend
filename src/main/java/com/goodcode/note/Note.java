package com.goodcode.note;

import com.goodcode.notebook.Notebook;
import com.goodcode.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Note {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "avatar", nullable = true, updatable = true)
    private String avatar;

    @Column(name = "title", nullable = true, updatable = true)
    private String title;

    @Column(name = "description", length = 1000, nullable = true, updatable = true)
    private String description;

    @Column(name = "body", length = 10000, nullable = false, updatable = true)
    private String body;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, updatable = true)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notebook_id", referencedColumnName = "id", nullable = true, updatable = true)
    private Notebook notebook;

    @Column(name = "updated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
