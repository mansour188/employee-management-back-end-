package com.example.employe.management.Repo;

import com.example.employe.management.model.LeaveRequest;
import com.example.employe.management.model.StateLeaveRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LeaveRequestRepository extends CrudRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findAllByAcceptedOrderByCreatedAsc(StateLeaveRequest state);
    List<LeaveRequest> findByEmployerUserId(Integer userId);
    List<LeaveRequest> findAllByEmployerUserIdAndDebutDateBetween(Integer userId, LocalDate startOfYear, LocalDate endOfYear);
    @Query(nativeQuery = true,value = "SELECT lr FROM leave_request lr WHERE YEAR(lr.debutDate) = :currentYear")
    List<LeaveRequest> findAllInCurrentYear(int currentYear);
    List<LeaveRequest> findTop3ByEmployerUserIdOrderByCreatedDesc(Integer userId);

}
