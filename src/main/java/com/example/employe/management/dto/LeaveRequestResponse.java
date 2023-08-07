package com.example.employe.management.dto;

import com.example.employe.management.model.StateLeaveRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestResponse {
    private Integer Id;
    private LocalDate debutDate;
    private LocalDate  finDate;
    private LocalDate created;
    private StateLeaveRequest accepted;
}
