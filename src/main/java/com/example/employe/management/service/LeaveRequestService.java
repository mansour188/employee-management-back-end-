package com.example.employe.management.service;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.Repo.LeaveRequestRepository;
import com.example.employe.management.dto.LeaveRequestDto;
import com.example.employe.management.model.LeaveRequest;
import com.example.employe.management.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class LeaveRequestService {
    @Autowired

    private LeaveRequestRepository leaveRequestRepository;
    @Autowired

    private EmployerRepository employerRepository;


    public void sendLeaveRequest(Integer userId , LeaveRequestDto leaveRequestDto){
        Optional<Users> employee=employerRepository.findById(userId);
        if (employee.isPresent()){
            LeaveRequest leaveRequest=new LeaveRequest();
            leaveRequest.setAccepted(false);
            leaveRequest.setDebutDate(leaveRequestDto.getDebutDate());
            leaveRequest.setFinDate(leaveRequestDto.getFinDate());
            leaveRequest.setUser(employee.get());
            leaveRequestRepository.save(leaveRequest);
        }else {
            throw new IllegalArgumentException("employee wth id= " + userId + " not found");
        }



    }
    public List<LeaveRequest> getAllLeaveRequest(){
       Iterable<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        return StreamSupport.stream(leaveRequests.spliterator(), false)  //convert Iterable to list
               .collect(Collectors.toList());
  }

    public List<LeaveRequest> getAcceptedLeaveRequest(){
      return leaveRequestRepository.findByAcceptedTrue();
  }
    public List<LeaveRequest> getNonAcceptedLeaveRequest(){
        return leaveRequestRepository.findByAcceptedFalse();
    }

    public List<LeaveRequest> getLeaveRequestByuserId(Integer userId){
      return leaveRequestRepository.findByUserUserId(userId);
    }

    public  void acceptLeaveRequest(Integer leaveRequestId ){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new NoSuchElementException("LeaveRequest not found"));

        leaveRequest.setAccepted(true);

        leaveRequestRepository.save(leaveRequest);

    }
   public void rejectLeaveRequest(Integer leaveRequestId ){
       LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
               .orElseThrow(() -> new NoSuchElementException("LeaveRequest not found"));

       leaveRequest.setAccepted(false);

       leaveRequestRepository.save(leaveRequest);

   }

    public List<LeaveRequest> getLeaveRequestsByUserIdInCurrentYear(Integer userId) {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate endOfYear = LocalDate.now().withDayOfYear(365);

        return leaveRequestRepository.findAllByUserUserIdAndDebutDateBetween(userId, startOfYear, endOfYear);
    }


    public List<LeaveRequest> getLeaveRequestsInCurrentYear() {
        int currentYear = Year.now().getValue();
        return leaveRequestRepository.findAllInCurrentYear(currentYear);
    }
}
