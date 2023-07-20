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
public class Department {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer depId;
    private String depName;
    private String Manger;
    private String description;
    @OneToMany(mappedBy = "department")
    private List<Users> employees;

}
