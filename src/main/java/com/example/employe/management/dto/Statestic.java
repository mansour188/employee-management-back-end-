package com.example.employe.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statestic {
    private Integer nbrEmloyees;
    private Integer nbrWork;
    private Integer nbrWorkToday;
    private Integer nbrPendingRequest;

}
