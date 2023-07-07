package com.example.employe.management.Repo;

import com.example.employe.management.model.Project;
import com.example.employe.management.model.Users;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepositorty   extends CrudRepository<Project, Integer> {
}
