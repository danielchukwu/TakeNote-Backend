package com.goodcode.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    List<Note> findAllByUserId(UUID userId);
    List<Note> findAllByNotebookId(UUID notebookId);
}
