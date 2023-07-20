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
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class EmployeeService implements UserDetailsService {


    @Autowired

    private BCryptPasswordEncoder passwordEncoder;
    @Autowired

    private EmployerRepository employerRepository;

    public Users addEmployer(EmployerDto employer, MultipartFile file) throws IOException {
        Users userExist= employerRepository.findByEmail(employer.getEmail());
        if (userExist!=null){
            throw new UserFoundException("email déjà exist");
        }
       Users newEmployer =new Users();
       String roleString=employer.getRole();
        System.out.println("#########################");
        System.out.println(roleString);
        if (roleString.equals("ADMIN")) {
            newEmployer.setRole(Role.ADMIN);
        } else if (roleString.equals("EMPLOYER")) {
            newEmployer.setRole(Role.EMPLOYER);
        } else if (roleString.equals("MANAGER")) {
            newEmployer.setRole(Role.MANAGER);
        } else {

            throw new IllegalArgumentException("Invalid role value: " + roleString);
        }

       newEmployer.setEmail(employer.getEmail());
       newEmployer.setFirstname(employer.getFirstname());
       newEmployer.setLastName(employer.getLastName());

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String targetDirectory = "media";
        String targetFilePath = targetDirectory + "/" + fileName;

        Path absolutePath = Paths.get("src/main/resources/static").toAbsolutePath();
        File targetFile = new File(absolutePath.toString(), targetFilePath);

        FileUtils.forceMkdirParent(targetFile);
        file.transferTo(targetFile);

       newEmployer.setImageUrl(fileName);
       newEmployer.setBirthDay(employer.getBirthDay());

       newEmployer.setPassword(passwordEncoder.encode(employer.getPassword()));
     return   employerRepository.save(newEmployer);
    }


    @Override
    public UserDetails loadUserByUsername(java.lang.String username) throws UsernameNotFoundException {
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
        List<UserResponse> employers = new ArrayList<>();
        users.forEach((u)->{
            UserResponse e=new UserResponse();
            e.setDepartment(u.getDepartment());
            e.setProject(u.getProject());
            e.setBirthDay(u.getBirthDay());
            e.setEmail(u.getEmail());
            e.setFirstname(u.getFirstname());
            e.setLastName(u.getLastName());
            System.out.println(u.getImageUrl());

            try {
                byte[] imageData = Files.readAllBytes(Paths.get("src/main/resources/static/media/" + u.getImageUrl()));
                e.setImageData(imageData);
            } catch (IOException error) {
                error.printStackTrace(); // Log the exception to identify any errors during image reading


            }
            employers.add(e);


        });
        return employers;
    }
    public List<UserResponse> getAllManger(){
        List<Users> users=employerRepository.findByRole(Role.MANAGER);
        List<UserResponse> managers = new ArrayList<>();
        users.forEach((u)->{
            UserResponse e=new UserResponse();
            e.setDepartment(u.getDepartment());
            e.setProject(u.getProject());
            e.setBirthDay(u.getBirthDay());
            e.setEmail(u.getEmail());
            try {
                byte[] imageData = Files.readAllBytes(Paths.get("src/main/resources/static/media/" + u.getImageUrl()));
                e.setImageData(imageData);
            } catch (IOException error) {
                error.printStackTrace(); // Log the exception to identify any errors during image reading


            }
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


    public void findRoleByToken(java.lang.String token){


    }
}
