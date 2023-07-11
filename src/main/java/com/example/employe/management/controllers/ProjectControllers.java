package com.example.employe.management.controllers;

import com.example.employe.management.Repo.ProjectRepositorty;
import com.example.employe.management.model.Project;
import com.example.employe.management.model.Users;
import com.example.employe.management.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectControllers {
    @Autowired
    private ProjectService projectService;
    @PostMapping("/")
    public void createProject(@RequestBody Project project) throws Exception {
        projectService.createProject(project);

    }

    @GetMapping("/")
    public List<Project> getAllProject(){
        return projectService.getAllProject();

    }
    @PutMapping("/{projectId}")
   public void updateProject(@PathVariable("projectId") Integer projectId,@RequestBody Project project){
        projectService.updateProject(projectId,project);


    }

    @DeleteMapping("/{projectId}")
    public void deleteProjectById(@PathVariable("projectId") Integer projectId){
         projectService.deleteProjectById(projectId);
    }
    @PostMapping("/addEmployer/{projectId}")
    public  void AddEmployerToProject(@PathVariable("projectId") Integer projectId, @RequestBody List<Integer> employeeIds){
        projectService.AddEmployerToProject(projectId,employeeIds);

    }
}
