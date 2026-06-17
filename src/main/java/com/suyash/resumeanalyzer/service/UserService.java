package com.suyash.resumeanalyzer.service;

import com.suyash.resumeanalyzer.model.User;
import com.suyash.resumeanalyzer.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@Service
public class UserService
{
    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder,UserRepository repository,JwtService jwtService)
    {
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
        this.repository = repository;
    }
    public User signup(User user)
    {
        if(repository.findByUsername(
                user.getUsername()) != null)
        {
            throw new RuntimeException(
                    "Username already exists"
            );
        }
        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()
                )
        );

        return repository.save(user);
    }
    public ResponseEntity<Map<String, String>>
    login(User user)
    {
        User dbUser =
                repository.findByUsername(
                        user.getUsername()
                );

        if(dbUser == null)
        {
            return ResponseEntity
                    .status(404)
                    .body(
                            Map.of(
                                    "message",
                                    "User not found"
                            )
                    );
        }

        if(!passwordEncoder.matches(
                user.getPassword(),
                dbUser.getPassword()
        ))
        {
            return ResponseEntity
                    .status(401)
                    .body(
                            Map.of(
                                    "message",
                                    "Wrong password"
                            )
                    );
        }

        String token =
                jwtService.generateToken(
                        user.getUsername()
                );

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Login successful 😎",
                        "token",
                        token
                )
        );
    }
}