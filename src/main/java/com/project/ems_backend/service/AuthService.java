package com.project.ems_backend.service;

import com.project.ems_backend.dto.LoginRequest;
import com.project.ems_backend.dto.LoginResponse;
import com.project.ems_backend.dto.ResetPasswordRequest;
import com.project.ems_backend.entity.Users;
import com.project.ems_backend.repository.UsersRepository;
import com.project.ems_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request)
    {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserName(),
                        request.getPassword()
                )
        );

        Users users=usersRepository.findByUserName(request.getUserName()).orElseThrow(()->
                new RuntimeException("User not found"));

        // Step 2: isActive check pannrom
        if (!users.getIsActive()) {
            throw new RuntimeException("Account is disabled!");
        }

        if(!passwordEncoder.matches(request.getPassword(),users.getPassword()))
        {
            throw new RuntimeException("Invalid Credentials");
        }

        // lastLogin update pannrom
        users.setLastLogin(LocalDateTime.now());
        usersRepository.save(users);

        String token=jwtUtil.genereteToken(users.getUserName(),users.getRole().name());
        return new LoginResponse(token,users.getRole().name(),users.getUserName(),users.getIsPasswordReset());
    }


    public String resetPassword(ResetPasswordRequest request)
    {
        Users users=usersRepository.findByUserName(request.getUserName()).orElseThrow(()->new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getOldPassword(),users.getPassword()))
        {
            throw new RuntimeException("Old password incorrect!");
        }

        users.setPassword(passwordEncoder.encode(request.getNewPassword()));
        users.setIsPasswordReset(true);
        usersRepository.save(users);
        return "Password reset successfully!";
    }


}
