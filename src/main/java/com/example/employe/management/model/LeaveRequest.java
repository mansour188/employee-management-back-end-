package com.example.employe.management.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    private String debutDate;
    private String finDate;
    private boolean accepted;

    @OneToOne(mappedBy = "LeaveRequest")
    private Users user;



}
