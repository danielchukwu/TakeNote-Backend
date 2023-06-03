package com.goodcode.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// CHAPTER 7

// Next we want to tell spring which configuration to use in order for our entire
// Authentication process to work. So this configuration is what we call the Binding.
// Therefore, we need to create a binding, Yes we created a filter, but it is not yet used.
// The binding allows us to use this filter

@Configuration         // Make this a Configuration Class
@EnableWebSecurity     // Enable Web Security
@RequiredArgsConstructor   // Any field added should be required in the constructor frm lombok
public class SecurityConfiguration {
    // At the start-up of the application spring would look for a @Bean of type SecurityFilterChain and this SecurityFilterChain is the Bean responsible for configuring all of our applications HTTP Security

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(                  // A: Explanation Ref down Below
                        requests -> requests
                                .requestMatchers("/api/v1/auth/register", "/api/v1/auth/authenticate")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session    // B: Explanation Ref down Below
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(Customizer.withDefaults())                          // Enable CORS
                .authenticationProvider(authenticationProvider)           // We want to tell spring lastly the authenticationProvider we will be using for our authentication
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)  // Use our `JwtAuthenticationFilter` before the UsernamePasswordAuthenticationFilter class
                .csrf(new Customizer<CsrfConfigurer<HttpSecurity>>() {    // Disable CSRF
                    @Override
                    public void customize(CsrfConfigurer<HttpSecurity> httpSecurityCsrfConfigurer) {
                        httpSecurityCsrfConfigurer.disable();  // Grab the csrfConfigurer and disable it
                    }
                });

        return http.build();
    }
}

// NEXT; CHAPTER 8 -> ApplicationConfig.java
// Over there we implement the `authenticationProvider` bean we used in our code above
// - `authenticationProvider(authenticationProvider)`

// A: Explanation Ref
// Real Configuration begins.
// Now in every security we do want to decide the patterns / urls that we want to secure and then the ones that we don't want to secure which are referred to as the whitelist
// Whitelists are Open endpoints. Endpoints that don't require authentications or tokens. For example endpoints that allow users register / login

//.requestMatchers("/login", "sign-up")     // Whitelisted endpoints / Open enpoints
//.permitAll()                              // Permit all the requests in our above list
//.anyRequest()                             // Grab all the other requests
//.authenticated()                          // They must be authenticated before permitted

// B: Explanation Ref
// We want to now see how we can configure the Session Management basically
// So we want a once per request filter which means every request should be authenticated
// this means we should not store the authentication state / the session state, It should be stateless and also that each request to our backend should be authenticated

// Set our Session Creation Policy to stateless, meaning sessions shouldn't be stored and for each request their should be a newley created session
