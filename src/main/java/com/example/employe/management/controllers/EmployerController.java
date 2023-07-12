package com.example.employe.management.controllers;

import com.example.employe.management.dto.EmployerDto;
import com.example.employe.management.dto.RegestrationRequest;
import com.example.employe.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployerController {
    @Autowired
    private UserService userService;
@PostMapping("register_employee")
    public void registerEmployee(@RequestBody EmployerDto employer){
    userService.addEmployer(employer);

}


}
