package com.example.employe.management.service;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.dto.EmployerDto;
import com.example.employe.management.model.Role;
import com.example.employe.management.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserService {
    @Autowired

    private EmployerRepository employerRepository;
    public Users addEmployer(EmployerDto employer){
       Users newEmployer =new Users();
       newEmployer.setRole(Role.EMPLOYER);
       newEmployer.setEmail(employer.getEmail());
       newEmployer.setFirstname(employer.getFirstname());


       newEmployer.setImageUrl(employer.getImageUrl());
       newEmployer.setBirthDay(employer.getBirthDay());

       newEmployer.setPassword(employer.getPassword());
     return   employerRepository.save(newEmployer);
    }



}
