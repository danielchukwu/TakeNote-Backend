package com.goodcode.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// CHAPTER 3

// 1.
// Component, Reository or Service. Helps us tell java to make this class a managed bean. We can use any one of them
@Component
// This tells Spring to make any field provided in these arg to be required
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Why we extended OncePerRequestFilter
    // This class helps tell that this class should be the first thing to execute on every request from the client.
    // without it, we would have an ordinary class. It gives us the method `doFilterInternal(){}`

    // 6. Inject JwtService - Allows us check if the token sent is valid and if it matches a user in our database
    private final JwtService jwtService;

    // Chap6: 1.
    private final UserDetailsService userDetailsService;


    // 2. Override method in OncePerRequest Filter
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 3. Grab Authorization from Header e.g "Authorization": "Bearer kJ3KlAZ2U987dCE232w..."
        final String authHeader = request.getHeader("Authorization");

        // 4. If header wasn't found / isn't valid. Jump to the next filter chain with `.doFilter()`
        //    and don't run the below code to authenticate the client any further
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. Grab the jwt token from the Header .ex final String jwt = "kJ3KlAZ2U987dCE232w..."
        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);
        // 7 - 8. Comments are down below


        // CHAPTER 6 (Also reference the Diagram at 📁src > main > resources > assets > AppsSecurityArchitecture.png)

        // Chap6: 1. Declare needed field `userDetailsService`

        // User Validation
        // STEP 1: check if user is already authenticated, if user already is then we don't need to go through the entire
        //         process of authenticating them again
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // STEP 2: Call the function we implemented in our ApplicationConfig.java which grabs a user record
            //         by their username(actually their Email)
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // STEP 3: If user token is valid. Update the SecurityContextHolder and send the client request to the
            //         DispatcherServlet and then the DispatcherServlet hands the request to the controller which then
            //         returns a response back to the client
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Grab the AuthToken
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                // Add additional details to our authToken
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // Update the SecurityContextHolder
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Pass response and request to the next filter in our filter chain
        filterChain.doFilter(request, response);
    }
    // NEXT CHAPTER AFTER 7 -> SecurityConfiguration.java
}

// NEXT: CHAPTER 4 -> JwtService.java

// 7 - 8. Comments
// 7. Next we need to add the 3 dependencies (jjwt-api, jjwt-impl, and jjwt-jackson) all from the same package,
//    NOTE: Reload Maven project after adding dependencies
//
// 📁 pom.xml
// <dependency>
//     <groupId>io.jsonwebtoken</groupId>
//     <artifactId>jjwt-api</artifactId>
//     <version>0.11.5</version>
// </dependency>
// <dependency>
//     <groupId>io.jsonwebtoken</groupId>
//     <artifactId>jjwt-impl</artifactId>
//     <version>0.11.5</version>
// </dependency>
// <dependency>
//     <groupId>io.jsonwebtoken</groupId>
//     <artifactId>jjwt-jackson</artifactId>
//     <version>0.11.5</version>
// </dependency>

// 8.
// CHECKOUT THE FILE: JwtExplanation.txt to understand more on Jwt before progressing
// After that we want to fully implement our JwtService.java file before coming back here to finish this filter up