package com.example.employe.management.service;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.Repo.ProjectRepositorty;
import com.example.employe.management.model.Project;
import com.example.employe.management.model.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Slf4j
@Service
public class ProjectService {

    @Autowired

    private ProjectRepositorty projectRepositorty;
    @Autowired
    private EmployerRepository employerRepository;
    public void createProject(Project project) throws Exception {
        Optional<Project> p=projectRepositorty.findByTitle(project.getTitle());
        if (p.isPresent()){
            throw new Exception("Project already exists!");
        }else {
            projectRepositorty.save(project);
        }
    }
    public List<Project> getAllProject(){
        Iterable<Project> projects = projectRepositorty.findAll();
        return StreamSupport.stream(projects.spliterator(), false)  //convert Iterable to list
                .collect(Collectors.toList());
    }

    public void updateProject(Integer projectId,Project project){
        Optional<Project> p=projectRepositorty.findById(projectId);
        System.out.println("*********************************************************");
        System.out.println(p.get());

        if (!p.isPresent()){
            log.info("project with id= " + projectId + " not found");
            throw new IllegalArgumentException("project with id= " + projectId + " not found");
        }else {
            project.setProjectID(projectId);
            projectRepositorty.save(project);
        }

    }

    public void deleteProjectById(Integer projectId){
        Optional<Project> p=projectRepositorty.findById(projectId);
        if (p.isPresent()){
            projectRepositorty.deleteById(projectId);
        }else {
            log.info("project with id= " + projectId + " not found");
            throw new IllegalArgumentException("project with id= " + projectId + " not found");
        }

    }

    public void AddEmployerToProject(Integer projectId, List<Integer> employeeIds){
        Optional<Project> p=projectRepositorty.findById(projectId);
        if (p.isPresent()){
            Project project = p.get();

            Iterable<Users> empl = employerRepository.findAllById(employeeIds);
            List<Users> employees= StreamSupport.stream(empl.spliterator(), false)  //convert Iterable to list
                    .collect(Collectors.toList());
            project.getEmployees().addAll(employees);

          

            projectRepositorty.save(project);
        }else {
            log.info("project with id= " + projectId + " not found");
            throw new IllegalArgumentException("project with id= " + projectId + " not found");
        }

    }

}
