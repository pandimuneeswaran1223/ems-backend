package com.project.ems_backend.service;


import com.project.ems_backend.entity.Users;
import com.project.ems_backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImple implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users=usersRepository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("User not found: "+username));

        if (!users.getIsActive()) {
            throw new DisabledException("Account is disabled!");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(users.getUserName())
                .password(users.getPassword())
                .roles(users.getRole().name())
                .build();
    }
}
