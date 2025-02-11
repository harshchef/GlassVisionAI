package com.glassvisionai.glassvisionai.controller;

import com.glassvisionai.glassvisionai.entity.User;
import com.glassvisionai.glassvisionai.model.AuthRequest;
import com.glassvisionai.glassvisionai.service.MyUserDetailsService;
import com.glassvisionai.glassvisionai.service.UserService;
import com.glassvisionai.glassvisionai.util.JwtUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Glass Detection", description = "APIs for Glass Detection")  // Updated tag
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Operation(summary = "User Login", description = "Authenticates the user and returns a JWT token along with the user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "Username not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> createToken(@RequestBody AuthRequest authRequest) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // Generate JWT token
            String token = jwtUtil.generateToken(userDetails);

            // Fetch user from database to get user ID
            User user = userService.findByUsername(authRequest.getUsername());

            // Return both token and user ID in the response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getId()); // Include user ID
            return ResponseEntity.ok(response);

        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @Operation(summary = "User Registration", description = "Registers a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Username already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.saveUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
