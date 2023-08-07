package com.example.employe.management.Repo;

import com.example.employe.management.model.Work;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WorkRepository   extends CrudRepository<Work, Integer> {
    List<Work> findByEmployerUserId(Integer userId);
    List<Work> findByEmployerUserIdAndStartTimeBetween(Integer userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Work> findTop10ByEmployerUserIdOrderByStartTimeDesc(Integer userId);
    List<Work> findAllByStartTimeBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);


}
