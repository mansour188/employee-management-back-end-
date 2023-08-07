package com.example.employe.management.controllers;

import com.example.employe.management.dto.LeaveRequestDto;
import com.example.employe.management.dto.LeaveRequestMangementDto;
import com.example.employe.management.dto.LeaveRequestResponse;
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
    public void sendLeaveRequest(@PathVariable("userId") Integer userId, @RequestBody LeaveRequestDto leaveRequestDto){

        leaveRequestService.sendLeaveRequest(userId,leaveRequestDto);

    }

    @GetMapping("/All")
    public List<LeaveRequest> getAllLeaveRequest(){
        return leaveRequestService.getAllLeaveRequest();
    }

    @GetMapping("/accepted")
    public  List<LeaveRequest> getAcceptedLeaveRequest(){
        return leaveRequestService.getAcceptedLeaveRequest();
    }
    @GetMapping("/rejected")
    public  List<LeaveRequest> getNonAcceptedLeaveRequest(){
        return leaveRequestService.getNonRejectedLeaveRequest();
    }
    @GetMapping("/{userId}")
    public List<LeaveRequest> getLeaveRequestByuserId(@PathVariable("userId") Integer userId){
        return leaveRequestService.getLeaveRequestByuserId(userId);
    }

    @GetMapping("/CurrentYear/{userId}")
    public List<LeaveRequest> getLeaveRequestsByUserIdInCurrentYear(@PathVariable("userId") Integer userId){
        return leaveRequestService.getLeaveRequestsByUserIdInCurrentYear(userId);
    }
    @GetMapping("/thisYear")
    public List<LeaveRequest> getLeaveRequestsInCurrentYear(){
        return leaveRequestService.getLeaveRequestsInCurrentYear();
    }

    @GetMapping("/last/{userId}")
    public List<LeaveRequestResponse> getLastThreeByuserId(@PathVariable("userId") Integer userId){
        return leaveRequestService.getLastThreeByuserId(userId);
    }
    @GetMapping("/pending")
    public List<LeaveRequestMangementDto> getPendingLeaveReqest(){
        System.out.println("*********************************");
        return leaveRequestService.getPendingLeaveRequest();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLeaveRequestById(@PathVariable("id") Integer id){
         leaveRequestService.deleteLeaveRequestById(id);
    }

    @PutMapping("/reject/{id}")
    public void rejectLeaveRequest(@PathVariable("id") Integer id){
        leaveRequestService.rejectLeaveRequest(id);
    }
    @PutMapping("/accept/{id}")
    public void acceptLeaveRequest(@PathVariable("id") Integer id){

        leaveRequestService.acceptLeaveRequest(id);
    }



}