package dev.ash.jobsportal.controller;

import dev.ash.jobsportal.model.AppUser;
import dev.ash.jobsportal.repository.AppUserRepository;
import dev.ash.jobsportal.security.JwtUtil;
import dev.ash.jobsportal.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final AppUserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AppUser user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists!");
        }

        log.info("Password: {}", passwordEncoder.encode(user.getPassword()));

        AppUser newUser = AppUser.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .role("ADMIN")
                .build();
        log.info("new user created");

        userRepository.save(newUser);
        log.info("new user saved");
        // Create empty profile via service
        profileService.createEmptyProfileForUser(newUser);
        log.info("new user profile created");

        return ResponseEntity.ok("User registered successfully! Complete profile on login!");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody AppUser user) {
        Map<String, String> response = new HashMap<>();
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            AppUser currentUser = (AppUser) auth.getPrincipal();
            String token = jwtUtil.generateToken(currentUser);
            response.put("token", token);
            log.info("User login success");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("result","Login failed. Invalid credentials.");
            log.error("User login failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

//    @PostMapping("/logout")
//    public ResponseEntity<String> logout() {
//        //Stateless logout; frontend deletes token
//        return ResponseEntity.ok("Logout successful");
//    }
}
