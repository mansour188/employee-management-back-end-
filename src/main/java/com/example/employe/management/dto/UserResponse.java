package com.example.employe.management.dto;

import com.example.employe.management.model.Department;
import com.example.employe.management.model.Project;
import com.example.employe.management.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private String firstname;
    private String lastName;
    private String email;

    private String birthDay;
    private byte[] imageData;



    private Project project;

    private Department department;
}
