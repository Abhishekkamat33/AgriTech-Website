package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Respository.UserRepository;
import com.adhunikkethi.adhunnikkethi.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;  // Injected instance

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Map your userType enum to a GrantedAuthority with "ROLE_" prefix
        Collection<? extends GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + existingUser.getUserType().name())
        );

        return org.springframework.security.core.userdetails.User.builder()
                .username(existingUser.getEmail())
                .password(existingUser.getPassword())
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(existingUser.getStatus() != User.Status.ACTIVE) // disable if not active
                .build();
    }
}
