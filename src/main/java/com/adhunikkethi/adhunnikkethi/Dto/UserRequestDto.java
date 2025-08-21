package com.adhunikkethi.adhunnikkethi.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

 @Data
 @NoArgsConstructor
 @AllArgsConstructor
public class UserRequestDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private LocalDateTime registrationDate;

}
