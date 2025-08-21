package com.adhunikkethi.adhunnikkethi.Services;

import com.adhunikkethi.adhunnikkethi.Dto.UserRequestDto;
import com.adhunikkethi.adhunnikkethi.Dto.UserResponseDto;
import com.adhunikkethi.adhunnikkethi.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    UserResponseDto createUser(UserRequestDto userRequestDto);
    ResponseEntity<String> verify(User user); // Change here
    Optional<User> updateUser(Long id, User user);
    boolean deleteUser(Long id);
}
