package com.goodcode.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

// CHAPTER 2

// 1. Create our `UserRepository` Interface which will be our Data Access Object
// This repo provides us methods such as findAll, findById e.t.c. all to perform
// CRUD operations on our User Model we created in CHAPTER 1 (in our User.java)
public interface UserRepository extends JpaRepository<User, UUID> {

    // 2. Add custom method
    Optional<User> findByEmail(String email);
}

// NEXT: CHAPTER 3 -> JwtAuthenticationFilter.java