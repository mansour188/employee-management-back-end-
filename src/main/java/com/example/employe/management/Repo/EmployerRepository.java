package com.example.employe.management.Repo;

import com.example.employe.management.model.Role;
import com.example.employe.management.model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployerRepository extends CrudRepository<Users, Integer> {
    Users findByEmail(java.lang.String email);
    List<Users> findByRole(Role role);
}
