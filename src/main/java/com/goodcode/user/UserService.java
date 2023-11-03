package com.goodcode.user;

import com.goodcode.auth.AuthenticationResponse;
import com.goodcode.notebook.Notebook;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // READ
    public User getUser(UUID id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("not found"));
    }
    public List<User> getUsers() { return this.userRepository.findAll(); }

    // UPDATE
    public User updateUser(UUID id, User updatedUser) {
        User existingUser = this.userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found"));

        // Update - Avatar, Name, Updated At
        existingUser.setAvatar( updatedUser.getAvatar() != null ? updatedUser.getAvatar() : existingUser.getAvatar());
        existingUser.setName( updatedUser.getName() != null ? updatedUser.getName() : existingUser.getName());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return this.userRepository.save(existingUser);
    }

    // DELETE
    public void deleteUser(UUID id) {
        this.userRepository.deleteById(id);
    }

    // EXISTS
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
