package com.example.employe.management.controllers;

import com.example.employe.management.dto.EmployerDto;
import com.example.employe.management.dto.UserResponse;
import com.example.employe.management.service.EmployeeService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class EmployerController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("register_employee")
    public ResponseEntity<String> registerEmployee(@RequestBody @Valid EmployerDto employer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorFields = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList());

            String errorMessage = "Validation errors in fields: " + String.join(", ", errorFields);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        employeeService.addEmployer(employer);

        return ResponseEntity.ok("Request processed successfully");
    }

    @GetMapping("allEmployees")
    public List<UserResponse> getAllEmployees(){
        return employeeService.getAllEmployers();
    }
    @GetMapping("allManagers")
    public List<UserResponse> getAllManagers(){
        return employeeService.getAllManger();
    }
    @DeleteMapping("deleteEmployee/{id}")
    public void delete(@PathVariable("id") Integer id){
        employeeService.deleteEmployer(id);
    }
    @PutMapping("updateEmployee/{id}")
    public void update(@PathVariable("id") Integer id,@RequestBody UserResponse userResponse){
        employeeService.updateEmployer(id,userResponse);
    }



}
