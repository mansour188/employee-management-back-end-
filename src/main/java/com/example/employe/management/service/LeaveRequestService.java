package com.example.employe.management.service;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.Repo.LeaveRequestRepository;
import com.example.employe.management.dto.LeaveRequestDto;
import com.example.employe.management.dto.LeaveRequestMangementDto;
import com.example.employe.management.dto.LeaveRequestResponse;
import com.example.employe.management.dto.UserResponse;
import com.example.employe.management.model.LeaveRequest;
import com.example.employe.management.model.StateLeaveRequest;
import com.example.employe.management.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
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

            System.out.println(leaveRequestDto);
            Users employe=employee.get();
            System.out.println(employe);
            LeaveRequest leaveRequest=new LeaveRequest();
            leaveRequest.setEmployer(employe);
            leaveRequest.setAccepted(StateLeaveRequest.PENDING);
            leaveRequest.setDebutDate(leaveRequestDto.getDebutDate());
            leaveRequest.setFinDate(leaveRequestDto.getFinDate());
            leaveRequest.setEmployer(employee.get());
            leaveRequest.setCreated(LocalDate.now());
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
      return leaveRequestRepository.findAllByAcceptedOrderByCreatedAsc(StateLeaveRequest.ACCEPTED);
  }
    public List<LeaveRequest> getNonRejectedLeaveRequest(){
        return leaveRequestRepository.findAllByAcceptedOrderByCreatedAsc(StateLeaveRequest.REJECTED);
    }
    public List<LeaveRequestMangementDto> getPendingLeaveRequest(){
        List<LeaveRequest> leaveRequests= leaveRequestRepository.findAllByAcceptedOrderByCreatedAsc(StateLeaveRequest.PENDING);
        System.out.println(leaveRequests);
        List<LeaveRequestMangementDto> leaveRequestMangementDtos=new ArrayList<>();
        for (LeaveRequest leaveRequest:leaveRequests
             ) {
            LeaveRequestMangementDto leaveRequestMangementDto=new LeaveRequestMangementDto();
            leaveRequestMangementDto.setAccepted(leaveRequest.getAccepted());
            leaveRequestMangementDto.setCreated(leaveRequest.getCreated());
            leaveRequestMangementDto.setDebutDate(leaveRequest.getDebutDate());
            leaveRequestMangementDto.setFinDate(leaveRequest.getFinDate());
            leaveRequestMangementDto.setId(leaveRequest.getId());
            UserResponse resp=new UserResponse();
            Users empl=leaveRequest.getEmployer();
            resp.setFirstname(empl.getFirstname());
            resp.setLastName(empl.getLastName());
            resp.setProject(empl.getProject());
            resp.setDepartment(empl.getDepartment());
            resp.setEmail(empl.getEmail());
            resp.setBirthDay(empl.getBirthDay());
            resp.setId(empl.getUserId());
            try {
                byte[] imageData = Files.readAllBytes(Paths.get("src/main/resources/static/media/" + empl.getImageUrl()));
                resp.setImageData(imageData);
            } catch (IOException error) {
                error.printStackTrace(); // Log the exception to identify any errors during image reading


            }
            leaveRequestMangementDto.setEmployee(resp);
            leaveRequestMangementDtos.add(leaveRequestMangementDto);
        }
        return leaveRequestMangementDtos;
    }

    public List<LeaveRequest> getLeaveRequestByuserId(Integer userId){
      return leaveRequestRepository.findByEmployerUserId(userId);
    }

    public  void acceptLeaveRequest(Integer leaveRequestId ){
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new NoSuchElementException("LeaveRequest not found"));

        leaveRequest.setAccepted(StateLeaveRequest.ACCEPTED);

        leaveRequestRepository.save(leaveRequest);

    }
   public void rejectLeaveRequest(Integer leaveRequestId ){
       LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
               .orElseThrow(() -> new NoSuchElementException("LeaveRequest not found"));

       leaveRequest.setAccepted(StateLeaveRequest.REJECTED);

       leaveRequestRepository.save(leaveRequest);

   }

    public List<LeaveRequest> getLeaveRequestsByUserIdInCurrentYear(Integer userId) {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        LocalDate endOfYear = LocalDate.now().withDayOfYear(365);

        return leaveRequestRepository.findAllByEmployerUserIdAndDebutDateBetween(userId, startOfYear, endOfYear);
    }


    public List<LeaveRequest> getLeaveRequestsInCurrentYear() {
        int currentYear = Year.now().getValue();
        return leaveRequestRepository.findAllInCurrentYear(currentYear);
    }

    public List<LeaveRequestResponse> getLastThreeByuserId(Integer userId) {
        List<LeaveRequest> leaveRequestList =leaveRequestRepository.findTop3ByEmployerUserIdOrderByCreatedDesc(userId);
        List<LeaveRequestResponse> leaveRequestResponses =new ArrayList<>();
        for (LeaveRequest leaveRequest:leaveRequestList
             ) {
            LeaveRequestResponse leaveRequestResponse =new LeaveRequestResponse();
            leaveRequestResponse.setAccepted(leaveRequest.getAccepted());
            leaveRequestResponse.setId(leaveRequest.getId());
            leaveRequestResponse.setFinDate(leaveRequest.getFinDate());
            leaveRequestResponse.setDebutDate(leaveRequest.getDebutDate());
            leaveRequestResponse.setCreated(leaveRequest.getCreated());
            leaveRequestResponses.add(leaveRequestResponse);

        }
        return leaveRequestResponses;
    }

    public void deleteLeaveRequestById(Integer id) {
        Optional<LeaveRequest> leaveRequest=leaveRequestRepository.findById(id);
        if (leaveRequest.isPresent()){
            if (leaveRequest.get().getAccepted()==StateLeaveRequest.PENDING){
                leaveRequestRepository.deleteById(id);

            }
        }
        else {
            throw new IllegalArgumentException("no Leave Request with this id ="+id);
        }
    }

    public Integer NumberOfPendingRequest(){
        return getPendingLeaveRequest().size();
    }
}
