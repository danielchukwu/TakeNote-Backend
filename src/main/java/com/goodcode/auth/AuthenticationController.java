package com.goodcode.auth;

import com.goodcode.user.User;
import com.goodcode.user.UserRepository;
import com.goodcode.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// CHAPTER 9

// 1. Let's now add routes that enable us register and authenticate users
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    // Before Implementing this Controller Class. Complete all the below chapter dependencies
    private final AuthenticationService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        // Check that users email is unique
        if (userService.existsByEmail(request.getEmail())){
            return ResponseEntity.status(409).body(new AuthenticationResponse());
        }
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        // use service to authenticate
        return ResponseEntity.ok(authService.authenticate(request));
    }

}

// NOTE⁚ Before implementing our auth endpoints. Create and implement all the Chapter classes below
// NEXT⁚ CHAPTER 10 -> AuthenticationResponse.java
// NEXT⁚ CHAPTER 11 -> RegisterRequest.java
// NEXT⁚ CHAPTER 12 -> AuthenticationRequest.java
// NEXT⁚ CHAPTER 13 -> AuthenticationService.java


// DONEE 💥💥🤯🤯
// All endpoints under /api/v1/auth/** - should be whitelisted (Open) -> as we stated in our SecurityConfiguration.java
// but all other endpoints e.g /api/v1/notes should only be accessible to authenticated users

// Now let's open up POSTMAN and test out our endpoints