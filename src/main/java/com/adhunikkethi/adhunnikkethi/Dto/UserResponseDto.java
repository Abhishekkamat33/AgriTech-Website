package com.adhunikkethi.adhunnikkethi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String token;       // JWT token string
    private String tokenType;   // token type, e.g., "Bearer"
    private String name;    // username of logged in user
    private String email;       // email of the user (optional)
    private String userID;
    // You can add more user info fields if needed, but avoid sensitive info like password

}
