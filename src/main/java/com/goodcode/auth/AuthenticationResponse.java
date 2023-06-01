package com.goodcode.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// CHAPTER 10

// A simple class that has a field token

@Data          // provides setters and getters, equals and haschode methods, toString method e.t.c
@Builder       // makes this class a builder
@AllArgsConstructor     // adds a constructor that expects all class fields as args
@NoArgsConstructor      // adds a constructor with no args
public class AuthenticationResponse {

    private String token;

}
