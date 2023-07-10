package com.example.employe.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity

public class Users {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstname;
    private String lastName;
    private String email;
    private String password;
    private String birthDay;
    private String ImageUrl;
    private Role role;

    @ManyToOne
    private Project project;
    @ManyToOne
    private  Department department;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "LeaveRequest_id", referencedColumnName = "Id")
    private LeaveRequest LeaveRequest;
    @OneToMany(mappedBy = "employer")
    private List<Work> works;



}
