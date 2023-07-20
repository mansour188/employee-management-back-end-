package com.example.employe.management.dto;


import com.example.employe.management.model.Role;
import jakarta.validation.constraints.*;

public class EmployerDto {
   @NotNull

    private String firstname;
    @NotBlank

    private String lastName;
   @Email
   @NotBlank

    private String email;
    @Size(min =3)
    @NotBlank

    private String password;
    @NotNull

    private String birthDay;




    @NotBlank
    @Pattern(regexp = "^(ADMIN|EMPLOYER|MANAGER)$", message = "Invalid role. Allowed values: ADMIN, EMPLOYEE, MANAGER")
    private String role;



    public EmployerDto() {
    }




    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
