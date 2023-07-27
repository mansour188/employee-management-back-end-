package com.example.employe.management.controllers;

import com.example.employe.management.dto.EmployerDto;
import com.example.employe.management.dto.UserResponse;
import com.example.employe.management.service.EmployeeService;
import com.example.employe.management.validator.FileValidator;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@RestController
@RequestMapping("/api")
public class EmployerController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private  FileValidator fileValidator;


    //send image + json object employee

    @PostMapping(value = "register_employee" ,consumes = {MULTIPART_FORM_DATA_VALUE,APPLICATION_JSON_VALUE})
    public ResponseEntity<String> registerEmployee( @RequestPart("file") MultipartFile file,@RequestPart("employee")  @Valid  EmployerDto employer,BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            List<String> errorFields = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList());
            String errorMessage = "Validation errors in fields: " + String.join(", ", errorFields);

            return ResponseEntity.badRequest().body(errorMessage);}
        employeeService.addEmployer(employer,file);

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
    public ResponseEntity<String> update(@PathVariable("id") Integer id,@RequestPart("file") MultipartFile file,@RequestPart("employee")  @Valid  EmployerDto employer,BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            List<String> errorFields = bindingResult.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList());
            String errorMessage = "Validation errors in fields: " + String.join(", ", errorFields);

            return ResponseEntity.badRequest().body(errorMessage);}
        employeeService.updateEmployer(id,employer,file);

        return ResponseEntity.ok("Request processed successfully");
    }

     @GetMapping("getEmployee/{userId}")
    public UserResponse getEmployee(@PathVariable("userId") Integer userId){
        return employeeService.getEmployeeByid(userId);
     }



}
