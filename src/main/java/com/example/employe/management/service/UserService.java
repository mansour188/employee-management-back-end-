package com.example.employe.management.service;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.dto.EmployerDto;
import com.example.employe.management.model.Role;
import com.example.employe.management.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class UserService implements UserDetailsService {
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user= employerRepository.findByEmail(username);
        if (user==null){
            throw new UsernameNotFoundException("user with email "+username+" not exist");
        }else {
            log.info("user exist in database");
        }


        return new User(user.getEmail(),user.getPassword(),user.getAuthorities());
    }
}
