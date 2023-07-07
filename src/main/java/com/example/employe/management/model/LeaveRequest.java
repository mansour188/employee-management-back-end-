package com.example.employe.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    private String debutDate;
    private String finDate;
    @OneToOne(mappedBy = "LeaveRequest")
    private Users user;



}
