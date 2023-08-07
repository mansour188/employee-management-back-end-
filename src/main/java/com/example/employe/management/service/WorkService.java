package com.example.employe.management.service;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.Repo.WorkRepository;
import com.example.employe.management.dto.Workdto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public Workdto getWorkById(Integer workId){
        Optional<Work> w=workRepository.findById(workId);
        if (!w.isPresent()){
            new NoSuchElementException("Work with id = "+workId +"not found");
        }
        Work work=w.get();
        Workdto workdto=new Workdto();
        workdto.setDescription(work.getDescription());
        workdto.setTitle(work.getTitle());
        workdto.setDuration(work.getDuration());
        workdto.setWorkId(work.getWorkId());
        workdto.setUserId(work.getEmployer().getUserId());
        return workdto;

    }
    public void updateWork(Integer workId ,Work work) throws Exception {
        Optional<Work> w=workRepository.findById(workId);
        if(w.isPresent()){
           Work newWork=w.get();
           newWork.setWorkId(workId);
           newWork.setTitle(work.getTitle());
           newWork.setDuration(work.getDuration());
           newWork.setDescription(work.getDescription());
           newWork.setStartTime(work.getStartTime());

            workRepository.save(newWork);
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

    public List<Workdto> getAllWorkByUserId(Integer userId){
       List<Work>  works=workRepository.findByEmployerUserId(userId);
       List<Workdto> workdtos=new ArrayList<>();
        for (Work work:works
             ) {
            Workdto workdto=new Workdto();
            workdto.setDescription(work.getDescription());
            workdto.setTitle(work.getTitle());
            workdto.setDuration(work.getDuration());
            workdto.setWorkId(work.getWorkId());
            workdto.setStartTime(work.getStartTime());
            workdto.setUserId(work.getEmployer().getUserId());
            workdtos.add(workdto);


        }
       return workdtos ;
    }
    public List<Work> getWorksFromYesterdayByUserId(Integer userId) {
        LocalDateTime startDateTime = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endDateTime = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);

        return workRepository.findByEmployerUserIdAndStartTimeBetween(userId, startDateTime, endDateTime);
    }

    public  List<Workdto> getLastTenWorkByuserid(Integer id){
        List<Work> works= workRepository.findTop10ByEmployerUserIdOrderByStartTimeDesc(id);
        List<Workdto> workdtos=new ArrayList<>();
        for (Work work:works
        ) {
            Workdto workdto=new Workdto();
            workdto.setDescription(work.getDescription());
            workdto.setTitle(work.getTitle());
            workdto.setDuration(work.getDuration());
            workdto.setWorkId(work.getWorkId());
            workdto.setStartTime(work.getStartTime());
            workdto.setUserId(work.getEmployer().getUserId());
            workdtos.add(workdto);


        }
        return workdtos ;
    }

    public  Integer getNumberofAllTasks(){
       Iterable<Work> iterable=workRepository.findAll();
        return  StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()).size();
    }

    public Integer getNumberOfWorkToday(){
        LocalDateTime startOfDay = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.of(LocalDateTime.now().toLocalDate(), LocalTime.MAX);

        return workRepository.findAllByStartTimeBetween(startOfDay, endOfDay).size();
    }
}
