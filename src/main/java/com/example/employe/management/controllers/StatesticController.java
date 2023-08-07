package com.example.employe.management.controllers;

import com.example.employe.management.dto.Statestic;
import com.example.employe.management.service.EmployeeService;
import com.example.employe.management.service.LeaveRequestService;
import com.example.employe.management.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statestic")

public class StatesticController {
    @Autowired
    private WorkService workService;
    @Autowired
    private LeaveRequestService leaveRequestService;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public Statestic getStatestic(){
         Integer nbrEmloyees=employeeService.NumberOfemployees();
         Integer nbrWork=workService.getNumberofAllTasks();
         Integer nbrWorkToday=workService.getNumberOfWorkToday();
         Integer nbrPendingRequest=leaveRequestService.NumberOfPendingRequest();
         Statestic statestic=new Statestic();
         statestic.setNbrEmloyees(nbrEmloyees);
         statestic.setNbrWork(nbrWork);
         statestic.setNbrPendingRequest(nbrPendingRequest);
         statestic.setNbrWorkToday(nbrWorkToday);
         return statestic;
    }
}
