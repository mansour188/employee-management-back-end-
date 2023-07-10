package com.example.employe.management.Repo;

import com.example.employe.management.model.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkRepository   extends CrudRepository<Work, Integer> {
    List<Work> findByEmployerUserId(Integer userId);
}
