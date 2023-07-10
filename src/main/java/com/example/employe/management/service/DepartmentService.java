package com.example.employe.management.service;

import com.example.employe.management.Repo.DepartementRepository;
import com.example.employe.management.model.Department;
import com.example.employe.management.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Service
public class DepartmentService {
    private DepartementRepository departementRepository;
    public List<Department> getAllDepartements(){
        Iterable<Department> departements = departementRepository.findAll();
        return StreamSupport.stream(departements.spliterator(), false)  //convert Iterable to list
                .collect(Collectors.toList());

                //(List<Department>) departementRepository.findAll();
    }
    public Department getDepartementByName(String nameDp){
       Optional<Department> departement=departementRepository.findByDepName(nameDp);
       return departement.orElseThrow(() -> new NoSuchElementException("Value not found"));
    }


    public Department createDepartment(Department department) throws Exception {
        Optional<Department> existingDepartment = departementRepository.findByDepName(department.getDepName());
        if (existingDepartment.isPresent()) {
            throw new Exception("Department already exists!");
        } else {
            return departementRepository.save(department);
        }



    }

    public void delateDepartement(Integer id) {
        try {
            departementRepository.deleteById(id);
        } catch (Exception e) {
            log.info("department wth id= " + id + " not found");
            throw new IllegalArgumentException("department wth id= " + id + " not found");

        }

    }


    public void addEmployeToDepartement(Integer depId, List<Users> employes) {
        Optional<Department> department = departementRepository.findById(depId);
        if (department.isPresent()) {
            Department updatedDepartment = department.get();
            List<Users> oldEmployer = updatedDepartment.getEmployees();
            oldEmployer.addAll(employes);
            updatedDepartment.setEmployees(oldEmployer);


        } else {
            throw new IllegalArgumentException("department wth id= " + depId + " not found");
        }


    }

    public void UpdateDepertement(Integer depId, Department department) {
        Optional<Department> d = departementRepository.findById(depId);
        if (d.isPresent()) {

            department.setDepId(depId);
            departementRepository.save(department);

        }else{
            throw new IllegalArgumentException("department wth id= "+depId+" not found");
        }

    }
}