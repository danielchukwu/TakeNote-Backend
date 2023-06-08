package com.goodcode.notebook;

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
public class Notebook {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "avatar", nullable = true, updatable = true)
    private String avatar;

    @Column(name = "title", nullable = true, updatable = true)
    private String title;

    @Column(name = "description", length = 2000, nullable = true, updatable = true)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, updatable = true)
    private User user;

    @Column(name = "updated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}