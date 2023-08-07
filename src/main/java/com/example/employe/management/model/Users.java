package com.example.employe.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity

public class Users  {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @NotNull
    private String firstname;
    @NotNull
    private String lastName;
    @NotNull
    @Email

    private String email;
    private String password;
    private String birthDay;
    private String ImageUrl;
    private Role role;


    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private Project project;
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private  Department department;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)

    private List<LeaveRequest> LeaveRequest;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Work> works;



    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getRole().name()));
        return authorities;
    }
}
