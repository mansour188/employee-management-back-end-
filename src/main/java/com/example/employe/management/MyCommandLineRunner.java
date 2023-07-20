package com.example.employe.management;

import com.example.employe.management.Repo.EmployerRepository;
import com.example.employe.management.model.Role;
import com.example.employe.management.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    EmployerRepository employerRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void run(java.lang.String... args) throws Exception {

        System.out.println("Application started.");
        Users admin=new Users();
        admin.setEmail("admin@admin.com");
        admin.setFirstname("admin");
        admin.setRole(Role.ADMIN);
        admin.setLastName("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        employerRepository.save(admin);




        System.out.println("Command-line  executed successfully.");
    }
}