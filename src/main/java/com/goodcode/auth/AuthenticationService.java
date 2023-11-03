package com.goodcode.auth;
import com.goodcode.config.JwtService;
import com.goodcode.user.Role;
import com.goodcode.user.User;
import com.goodcode.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// CHAPTER 13

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register (RegisterRequest request) {
        // Create a user object using request body sent by the client
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        // save the user to our database
        repository.save(user);
        // generate a jwt token for our new user
        var jwtToken = jwtService.generateToken(user);

        // return token
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(new UserDTO(user.getId(), user.getAvatar(), user.getName(), user.getEmail()))
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // authenticate the user using the request email and password
        // if authentication is unsuccessful it throws an AuthenticationException
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( request.getEmail(), request.getPassword())
        );

        // find user by their email
        var user = repository.findByEmail(request.getEmail()).orElseThrow();
        // generate a jwt token for our new user
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .user(new UserDTO(user.getId(), user.getAvatar(), user.getName(), user.getEmail()))
                .build();
    }
}
