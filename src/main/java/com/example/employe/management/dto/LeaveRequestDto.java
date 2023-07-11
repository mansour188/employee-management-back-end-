package com.example.employe.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeaveRequestDto {
    private LocalDate debutDate;
    private LocalDate  finDate;

}
