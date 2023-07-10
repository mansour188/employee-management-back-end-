package com.example.employe.management.Repo;

import com.example.employe.management.model.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DepartementRepository extends CrudRepository<Department, Integer> {
    Optional<Department> findByDepName(String nameDp);
}
