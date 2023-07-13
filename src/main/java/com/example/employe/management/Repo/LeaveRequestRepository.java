package com.example.employe.management.Repo;

import com.example.employe.management.model.LeaveRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends CrudRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findByAcceptedTrue();
    List<LeaveRequest> findByAcceptedFalse();
    List<LeaveRequest> findByUserUserId(Integer userId);
    List<LeaveRequest> findAllByUserUserIdAndDebutDateBetween(Integer userId, LocalDate startOfYear, LocalDate endOfYear);
    @Query("SELECT lr FROM LeaveRequest lr WHERE YEAR(lr.debutDate) = :currentYear")
    List<LeaveRequest> findAllInCurrentYear(int currentYear);
}
