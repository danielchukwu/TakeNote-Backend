package com.goodcode.config;

// Chapter 4

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// 1. Create our JwtService
@Service
public class JwtService {

    // 4. Create variable for storing the sign-in key. To Get a signInKey follow the below steps
    // - Visit https://www.allkeysgenerator.com/
    // - Select Encryption Key > Select 256-bit > Select Hex ✅ > Copy generated key and paste below
    final String SECRET_KEY = "576D5A7134743777217A25432A462D4A614E645267556B58703272357538782F";

    // 7. Extracting only the username claim from the token
    //    Note: what our username claim for our application will actually be is the users Email.
    //    But in Jwt the subjects are called username by convention, so we stick to that convention
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 8. This method helps us generate a token using the extra claims we want and springs security userDetails object
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)      // Adding extra claims to the token
                .setSubject(userDetails.getUsername())   // setting the subject of the claim
                .setIssuedAt(new Date(System.currentTimeMillis()))   // When this claim was created
                .setExpiration(new Date(System.currentTimeMillis() + 100_000 * 60 * 24))    // This claims Expiration date
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)    // Sign the token with our sign-in key and specify the algorithm to use
                .compact();    // Generates and then returns our final token
    }

    // 9. Generating a token without the extraClaims and only use Springs UserDetails object
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // 10. This method checks if a token is valid. If it's username matches that of the
    //     userDetails and if the token hasn't expired then it is a valid token and this should
    //     return true, if any one of them isn't true then the token is not valid and this method
    //     should return false
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // 11. This Method takes a token and checks if it has expired or not
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 12. This method extracts the expiration date from a passed in token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 13. DONE. Now that we are done with our JwtService, lets go back and complete the implementation of our Filter.
    //           But before that lets set up a UserDetailsService that fetches a user based on their Email
    // NEXT: CHAPTER 5 -> ApplicationConfig.java

    // 1. Implementing a method that extracts all the claims from the token
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()                 // Parse the token using this
                .setSigningKey(getSignInKey())   // Set sign-in key for decoding the token
                .build()                         // We need to then build because this is a builder
                .parseClaimsJws(token)           // Next we parse the claims in the token
                .getBody();                      // After parsing the token we can then grab the entire claim body

    }

    // 2. What is a SignInKey and Why do we need one?
    //      A sign-in key is a secret that is used to digitally sign a JWT. It is used to create the signature part
    //      of the JWT which is used to verify that the sender of the JWT is who they claim to be and ensure that the
    //      message wasn't changed along the way
    // Pro Tip:  The sign-in key is used in conjunction with the sign-in algorithm specified in the JWT Header
    //           {"alg": "HS256", ...} to create the SIGNATURE.
    //           The specific sign-in algorithm and key size {"alg": "HS256", ...}, would depend on the
    //           security requirement of your application and the level of trust you have in the sign-in party
    //
    // NEXT: We want to generate a sign-in key of size 256, there are tons of tools out there for doing this so
    //       we'll be making use of one of them
    // 3. Assignment: Declare a variable for this above
    // 5. Implement
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 6. Implement a method that can extract a single claim that we want
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
}

// NEXT: CHAPTER 5 -> ApplicationConfig.java
// Set up our own implementation of the UserDetailsService that allows us use the UserRepository to fetch
// a user from the database using the UserRepository provided method - `findByEmail(...)`