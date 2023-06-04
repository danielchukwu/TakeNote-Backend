package com.goodcode.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


// CHAPTER 10

// A simple class that has a field token

@Data          // provides setters and getters, equals and hashcode methods, toString method e.t.c
@Builder       // makes this class a builder
@AllArgsConstructor     // adds a constructor that expects all class fields as args
@NoArgsConstructor      // adds a constructor with no args
public class AuthenticationResponse {

    private String token;
    private UserDTO user;

}

record UserDTO(UUID id, String avatar, String name, String email){}