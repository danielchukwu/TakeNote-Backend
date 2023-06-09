package com.goodcode.user;


import com.goodcode.note.Note;
import com.goodcode.notebook.Notebook;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

// CHAPTER 1

// 1
// Lombok Annotation - reduces boilerplate code by adding a constructor,
// getters and setters, equals and hashcode functions, toString methods etc...
@Data
@Builder  // Builds our entity using a design pattern builder
@NoArgsConstructor   // adds a no arguments constructor ex. public User() {}
@AllArgsConstructor  // adds an all arguments constructor ex. public User(..., all the fields, ...) {...}
@Entity   // Tells JPA to use hibernate and create a table in our db using this class. Basically make this class an Entity in our db
@Table(name = "_user")   // renames the table in the database to _user so that it doesn't conflict with the user table that exists by default in postgres
public class User implements UserDetails {

    // 2. Add the fields to be used to create columns for the _user table
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "avatar", nullable = true, updatable = true)
    private String avatar;
    @Column(name = "name", nullable = false, updatable = true, length = 50)
    private String name;
    @Column(name = "email", nullable = false, updatable = true, unique = true)
    private String email;
    @Column(name = "password", nullable = false, updatable = true)
    private String password;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Notebook> notebooks;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Note> notes;
    @Column(name = "updated_at", nullable = false, updatable = true)
    private LocalDateTime updatedAt;
    @Column(name = "created_at", nullable = false, updatable = true)
    private LocalDateTime createdAt;

    // 3. Create the Enum type and add a field for it
    @Enumerated(EnumType.STRING)
    private Role role;


    // 5. All the methods below are from UserDetails implemented Interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority( role.name() ) );
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


// NEXT: CHAPTER 2 -> UserRepository.java