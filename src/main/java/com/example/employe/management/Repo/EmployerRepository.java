package com.example.employe.management.Repo;

import com.example.employe.management.model.Users;
import org.springframework.data.repository.CrudRepository;

public interface EmployerRepository extends CrudRepository<Users, Integer> {
}
