package com.example.employe.management.controllers;


import com.example.employe.management.model.Department;
import com.example.employe.management.model.Users;
import com.example.employe.management.service.DepartmentService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/departement")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/")
    public ResponseEntity createDepartment(@RequestBody Department department) throws Exception {
        departmentService.createDepartment(department);
       return ResponseEntity.ok("created");
    }
    @GetMapping("/")
    public ResponseEntity<List<Department>> getAllDepartement(){
        List<Department> departments=departmentService.getAllDepartements();
        return ResponseEntity.ok(departments);
    }
    @GetMapping("/{name}")
    public ResponseEntity<Department> getDepartementById(@RequestParam("name") String name){
        Department department=departmentService.getDepartementByName(name);
        return ResponseEntity.ok(department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepartement(@RequestParam("id") Integer id){
        departmentService.delateDepartement(id);
        return ResponseEntity.ok("delated");
    }
    @PostMapping("/addEmployee/{id}")
    public void addEmployeToDepartement(@PathVariable("id") Integer id , List<Integer> employeeIds){
        departmentService.addEmployeToDepartement(id,employeeIds);
    }

    @PutMapping("/{id}")
    public void UpdateDepertement(@PathVariable("id") Integer id, @RequestBody Department department){
        departmentService.UpdateDepertement(id,department);
    }


}
