package com.example.employe.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity

public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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


}
