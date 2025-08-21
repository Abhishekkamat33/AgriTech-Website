package com.adhunikkethi.adhunnikkethi.controllers;

import com.adhunikkethi.adhunnikkethi.Dto.UserRequestDto;
import com.adhunikkethi.adhunnikkethi.Dto.UserResponseDto;
import com.adhunikkethi.adhunnikkethi.Respository.UserRepository;
import com.adhunikkethi.adhunnikkethi.Services.UserService;
import com.adhunikkethi.adhunnikkethi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    private UserService userService;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userService.createUser(userRequestDto);  // service handles mapping & saving
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        ResponseEntity<String> verifyResponse = userService.verify(user);

        if (!verifyResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(verifyResponse.getStatusCode())
                    .body(Map.of("error", verifyResponse.getBody()));
        }

        return ResponseEntity.ok(Map.of("token", verifyResponse.getBody()));
    }




    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setStatus(userDetails.getStatus());
            user.setUserType(userDetails.getUserType());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
