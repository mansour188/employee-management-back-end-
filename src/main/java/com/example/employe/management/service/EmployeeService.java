package com.example.employe.management.service;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.dto.EmployerDto;
import com.example.employe.management.dto.UserResponse;
import com.example.employe.management.exception.UserFoundException;
import com.example.employe.management.model.Role;
import com.example.employe.management.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class EmployeeService implements UserDetailsService {


    @Autowired

    private BCryptPasswordEncoder passwordEncoder;
    @Autowired

    private EmployerRepository employerRepository;
    public Users addEmployer(EmployerDto employer){
        Users userExist= employerRepository.findByEmail(employer.getEmail());
        if (userExist!=null){
            throw new UserFoundException("email déjà exist");
        }
       Users newEmployer =new Users();
       newEmployer.setRole(Role.EMPLOYER);
       newEmployer.setEmail(employer.getEmail());
       newEmployer.setFirstname(employer.getFirstname());
       newEmployer.setLastName(employer.getLastName());


       newEmployer.setImageUrl(employer.getImageUrl());
       newEmployer.setBirthDay(employer.getBirthDay());

       newEmployer.setPassword(passwordEncoder.encode(employer.getPassword()));
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
    public List<UserResponse> getAllEmployers(){
        List<Users> users=employerRepository.findByRole(Role.EMPLOYER);
        List<UserResponse> employers = null;
        users.forEach((u)->{
            UserResponse e=new UserResponse();
            e.setDepartment(u.getDepartment());
            e.setProject(u.getProject());
            e.setBirthDay(u.getBirthDay());
            e.setEmail(u.getEmail());
            e.setImageUrl(u.getImageUrl());
            e.setFirstname(u.getFirstname());
            e.setLastName(u.getLastName());
            employers.add(e);


        });
        return employers;
    }
    public List<UserResponse> getAllManger(){
        List<Users> users=employerRepository.findByRole(Role.MANAGER);
        List<UserResponse> managers = null;
        users.forEach((u)->{
            UserResponse e=new UserResponse();
            e.setDepartment(u.getDepartment());
            e.setProject(u.getProject());
            e.setBirthDay(u.getBirthDay());
            e.setEmail(u.getEmail());
            e.setImageUrl(u.getImageUrl());
            e.setFirstname(u.getFirstname());
            e.setLastName(u.getLastName());

            managers.add(e);


        });
        return managers;
    }
    public void deleteEmployer(Integer id){
        employerRepository.deleteById(id);
    }
    public void updateEmployer(Integer id ,UserResponse userResponse){
        Optional<Users> opt=employerRepository.findById(id);
        if (opt.isPresent()){
            Users empl=opt.get();
            empl.setLastName(userResponse.getLastName());
            empl.setFirstname(userResponse.getFirstname());
            empl.setEmail(userResponse.getEmail());
            empl.setDepartment(userResponse.getDepartment());
            empl.setProject(userResponse.getProject());


        }
    }
}
