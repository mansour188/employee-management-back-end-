package com.example.employe.management.dto;

import com.example.employe.management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegestrationRequest {
    private Integer userId;
    private String firstname;
    private String lastName;
    private String email;
    private String password;
    private String birthDay;
    private String ImageUrl;
    private Role role;
}
