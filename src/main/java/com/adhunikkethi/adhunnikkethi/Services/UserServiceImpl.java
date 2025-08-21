package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Dto.UserRequestDto;
import com.adhunikkethi.adhunnikkethi.Dto.UserResponseDto;
import com.adhunikkethi.adhunnikkethi.Respository.UserRepository;
import com.adhunikkethi.adhunnikkethi.entities.User;
import com.adhunikkethi.adhunnikkethi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authmanager;


    @Autowired
    private JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setEmail(userRequestDto.getEmail());
        user.setPassword(userRequestDto.getPassword());
        user.setPhone(userRequestDto.getPhone());
        user.setRegistrationDate(LocalDateTime.now());
        user.setAddress(userRequestDto.getAddress());



        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }


        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign a default role if none provided
        if (user.getUserType() == null) {
            user.setUserType(User.UserType.FARMER);
        }


        if (user.getStatus() == null) {
            user.setStatus(User.Status.ACTIVE);
        }

        User savedUser = userRepository.save(user);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserID(String.valueOf(savedUser.getUserId()));
        userResponseDto.setName(savedUser.getName());
        userResponseDto.setEmail(savedUser.getEmail());


        return userResponseDto;
    }

    @Override
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setUserType(userDetails.getUserType());
            user.setStatus(userDetails.getStatus());
            return userRepository.save(user);
        });
    }

    @Override
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public ResponseEntity<String> verify(User user) {
        try {
            Authentication authentication = authmanager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            if (authentication.isAuthenticated()) {
                Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());
                if (existingUserOpt.isPresent()) {
                    User existingUser = existingUserOpt.get();
                    String token = jwtUtil.generateToken(existingUser);
                    return ResponseEntity.ok(token); // 200 OK with token
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
            }
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }



}
