package com.example.employe.management.controllers;

import com.example.employe.management.dto.LeaveRequestDto;
import com.example.employe.management.model.Department;
import com.example.employe.management.model.LeaveRequest;
import com.example.employe.management.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaveRequest")
public class LeaveRequestController {
    @Autowired
    LeaveRequestService leaveRequestService;
    @PostMapping("/{userId}")
    public void sendLeaveRequest(@RequestParam("userId") Integer userId, LeaveRequestDto leaveRequestDto){

        leaveRequestService.sendLeaveRequest(userId,leaveRequestDto);

    }

    @GetMapping("/")
    public List<LeaveRequest> getAllLeaveRequest(){
        return leaveRequestService.getAllLeaveRequest();
    }

    @GetMapping("/accepted")
    public  List<LeaveRequest> getAcceptedLeaveRequest(){
        return leaveRequestService.getAcceptedLeaveRequest();
    }
    @GetMapping("/rejected")
    public  List<LeaveRequest> getNonAcceptedLeaveRequest(){
        return leaveRequestService.getNonAcceptedLeaveRequest();
    }
    @GetMapping("/{userId}")
    public List<LeaveRequest> getLeaveRequestByuserId(@PathVariable("userId") Integer userId){
        return leaveRequestService.getLeaveRequestByuserId(userId);
    }

    @GetMapping("/CurrentYear/{userId}")
    public List<LeaveRequest> getLeaveRequestsByUserIdInCurrentYear(@PathVariable("userId") Integer userId){
        return leaveRequestService.getLeaveRequestsByUserIdInCurrentYear(userId);
    }


}
