package com.goodcode.notebook;

import com.goodcode.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, UUID> {

    Notebook findByUserId(UUID userId);
    List<Notebook> findAllByUserId(UUID userId);

}
