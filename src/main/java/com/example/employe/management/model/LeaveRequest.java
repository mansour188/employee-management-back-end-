package com.example.employe.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data


@Entity

public class LeaveRequest {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer Id;
    private LocalDate debutDate;
    private LocalDate  finDate;
    private LocalDate created;
    private StateLeaveRequest accepted;


    @JsonIgnore
    @ToString.Exclude

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Users employer;


}
