package com.goodcode.config;

import com.goodcode.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// CHAPTER 5
// This app config would hold all our app configurations such as @beans and so on



// 1. By using the @Configuration Annotation - at the startup of this app spring would pick up this class
//    and try to implement and inject all the @Bean that we would declare within this `ApplicationConfig` class
@Configuration
@RequiredArgsConstructor   // In case we want to inject something .ex `UserRepository`, Lombok adds a required args constructor for this
public class ApplicationConfig {

    // 2. This repository would allow us to communicate with our _user table in the database
    private final UserRepository repository;

    // 3. Implement our own UserDetailsService Bean that fetches a user from our database by their email
    @Bean
    public UserDetailsService userDetailsService() {
        // Implements the `loadUserByUsername` method provided by the `UserDetailsService` Interface
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // CHAPTER 8

    // Chap 8: 1. The below is for `ApplicationConfig.java` to be implemented later on
    // This Bean of type AuthenticationProvider will be required later in our `SecurityConfiguration.java` file.
    // The `authenticationProvider` is a DAO responsible for fetching the user details and encoding the password.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // There are several implementations for the AuthenticationProvider, but we
        // will be using the DaoAuthenticationProvider
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Specify which detail service to use to fetch details about our user
        authProvider.setUserDetailsService(userDetailsService());
        // Specify a password encoder for the application. When authenticating a user this is used to decode the password using a particular algorithm.
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    // Chap 8: 2. This is a password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Chap 8: 3. The AuthenticationManager will help us manage our apps Authentications.
    // It has a bunch of methods that helps us achieve this so let's add a @Bean for this
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



    // NEXT: CHAPTER 9 -> AuthenticationController.java
    // LASTLY, we add our auth endpoints in a file named 📁auth > AuthenticationController.java
}

// NEXT: CHAPTER 6 -> JwtAuthenticationFilter.java
