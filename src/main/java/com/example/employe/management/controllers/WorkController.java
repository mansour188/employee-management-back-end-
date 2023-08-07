package com.example.employe.management.controllers;

import com.example.employe.management.dto.Workdto;
import com.example.employe.management.model.Work;
import com.example.employe.management.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
   private WorkService workService;

    @PostMapping("/post/{userId}")
    public void saveWorkByUserId(@PathVariable("userId") Integer userId, @RequestBody Work work){
        workService.saveWorkByUserId(userId,work);

    }
    @GetMapping("/get/{userId}")
    public List<Workdto> getAllWorkByUserId(@PathVariable("userId") Integer userId){

        return workService.getAllWorkByUserId(userId);
    }
    @GetMapping("/last/{userId}")
    List<Workdto> getLastThreeWorks(@PathVariable("userId") Integer userId){
        return workService.getLastTenWorkByuserid(userId);
    }
    @GetMapping("/yesterday/{userId}")
    List<Work> getWorksFromYesterdayByUserId(@PathVariable("userId") Integer userId){
        return workService.getWorksFromYesterdayByUserId(userId);
    }
    @DeleteMapping("/delete/{workId}")
    public void deleteWork(@PathVariable("workId") Integer workId){
        workService.deleteWork(workId);

    }
    @PutMapping("/update/{workId}")
    public  void updateWork(@PathVariable("workId") Integer workId,@RequestBody Work work) throws Exception {
        workService.updateWork(workId,work);
    }


}
