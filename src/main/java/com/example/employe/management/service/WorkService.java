package com.example.employe.management.service;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.Repo.WorkRepository;
import com.example.employe.management.model.Users;
import com.example.employe.management.model.Work;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Service
public class WorkService {
    @Autowired

    private EmployerRepository employerRepositor;
    @Autowired

    private WorkRepository workRepository;
    public void saveWorkByUserId(Integer userId, Work work) {
        Users user = employerRepositor.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with userId: " + userId));

        work.setEmployer(user);
        workRepository.save(work);
    }
    public Work getWorkById(Integer workId){
        Optional<Work> work=workRepository.findById(workId);
        return work.orElseThrow(() -> new NoSuchElementException("Work with id = "+workId +"not found"));

    }
    public void updateWork(Integer workId ,Work work) throws Exception {
        Optional<Work> w=workRepository.findById(workId);
        if(w.isPresent()){
            work.setWorkId(workId);
            workRepository.save(work);
        }else {
            throw new Exception("work with id = "+workId+" not exists!");
        }



    }
    public void deleteWork(Integer workId){
        Optional<Work> w=workRepository.findById(workId);
        if(w.isPresent()){
            workRepository.deleteById(workId);
        }


    }

    public List<Work> getAllWorkByUserId(Integer userId){
       return  workRepository.findByEmployerUserId(userId);
    }
    public List<Work> getWorksFromYesterdayByUserId(Integer userId) {
        LocalDateTime startDateTime = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);

        return workRepository.findByEmployerUserIdAndStartTimeBetween(userId, startDateTime, endDateTime);
    }
}
