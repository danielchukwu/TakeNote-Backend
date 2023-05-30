package com.goodcode.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
    final JwtService jwtService;


    // 2. Override method in OncePerRequest Filter
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain ) throws ServletException, IOException {
        // 3. Grab Authorization from Header e.g "Authorization": "Bearer kJ3KlAZ2U987dCE232w..."
        final String authHeader = request.getHeader("Authorization");

        // 4. If header wasn't found / isn't valid. Jump to the next filter chain with `.doFilter()`
        //    and don't run the below code to authenticate the client any further
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. Grab the jwt token from the Header .ex final String jwt = "kJ3KlAZ2U987dCE232w..."
        final String jwt_token = authHeader.substring(7);
        final String userEmail = jwtService.extractEmail(jwt_token);

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
        // JWT Explanation
        //
        // What is JWT Token
        // A JSON Web Token is a compact URL SAFE 🔒 means of representing `claims` to be transferred between two parties the client and the server.
        //
        //         The claims in JWT are encoded as JSON objects which are literrally signed using a JSON Web signature
        //
        // Visit https:// jwt.io/ to see an example representation of an Encoded JWT and A decoded version
        //
        // ENCODED FORMAT
        //
        // ⌈                                                                                            ⌉
        //         yJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwi
        //         aWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
        // ⌊                                                                                            ⌋
        //
        //         DECODED FORMAT
        //
        // ⌈ HEADER: ALGORITHM & TOKEN TYPE                                                             ⌉
        //         {
        //             "alg": "HS256",
        //                 "typ": "JWT"
        //         }
        // ⌊                                                                                            ⌋
        //
        // ⌈ PAYLOAD: DATA                                                                              ⌉
        //         {
        //             "sub": "1234567890",
        //                 "name": "John Doe",
        //                 "iat": 1516239022
        //         }
        // ⌊                                                                                            ⌋
        //
        // ⌈ VERIFY SIGNATURE                                                                           ⌉
        //         HMACSHA256(
        //                 base64UrlEncode(header) + "." +
        //                         base64UrlEncode(payload),
        //
        //                 your-256-bit-secret
        //         )
        // ⌊                                                                                            ⌋
        //
        // Explanation
        // The Decoded JWT consists of three parts
        //
        // A. HEADER
        // This consists of
        //         - The type of signing algorithm used by the token {"alg": "HS256", ...}
        // - It also holds the type of the token {..., "typ": "JWT"}
        // ⭐ It is held in the first part of the Token
        // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9. ------- . ------
        //
        //         B. PAYLOAD
        // The payload consists of the claims. Claims are statements about an Entity, typically the User.
        // ⭐ It is held in the middle part of the Token
        // ------. eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9 . ------
        //
        // There are 3 types of claims
        //         - Registered Claims:
        // These are a set of predefined claims which are not mandatory but recommended to provide
        // a set of useful claims. ISS, Subject, ODD, EXP etc...
        // - Public Claims:
        // These are claims that are defined within the IA & A JSON Web Token Registry or public by
        //         nature
        // - Private Claims:
        // These are custom claims to share information between parties that agree to use them
        //
        //
        // C. VERIFY SIGNATURE
        // This is used to verify that the center of the jwt token is who they claim to be and it
        // ensures that the message wasn't changed along the way
        // ⭐ It is held in the last part of the Token
        // ------- . ------ . eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9
        //
        //
        // NOW THAT WE HAVE A BASIC understanding of JWT Tokens. We can start implementing them


    }
}

// NEXT: CHAPTER 4 -> JwtService.java