package com.example.AuthenticationProject.dto;

import com.example.AuthenticationProject.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//contains the detILS WE NEED TO PROVIDE AS A USER TO REGISTR FOR THE 1ST TIME
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
