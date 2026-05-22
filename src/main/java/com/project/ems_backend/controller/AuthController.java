package com.project.ems_backend.controller;


import com.project.ems_backend.dto.LoginRequest;
import com.project.ems_backend.dto.ResetPasswordRequest;
import com.project.ems_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> restPassword(@RequestBody ResetPasswordRequest request)
    {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

}
