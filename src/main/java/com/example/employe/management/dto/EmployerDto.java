package com.example.employe.management.dto;


import javax.validation.constraints.NotBlank;

public class EmployerDto {
    @NotBlank

    private String firstname;
    @NotBlank

    private String lastName;
    @NotBlank

    private String email;
    @NotBlank

    private String password;
    @NotBlank

    private String birthDay;
    @NotBlank

    private String ImageUrl;




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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }


}
