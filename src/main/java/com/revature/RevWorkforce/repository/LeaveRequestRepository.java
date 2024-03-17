package com.revature.RevWorkforce.repository;

import com.revature.RevWorkforce.entity.LeaveRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Transactional
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    @Query("FROM leave_request WHERE account_id = :accountId ORDER BY start_date DESC")
    List<LeaveRequest> getLeaveRequestsByAccountId(@Param("accountId") Integer account_id);

    @Query("FROM leave_request ORDER BY start_date DESC")
    List<LeaveRequest> getAllLeaveRequests();
    
    @Query(
            nativeQuery = true,
            value = "SELECT leave_request.* FROM leave_request JOIN account ON account.account_id = leave_request.account_id " +
                    "WHERE account.managerId = :managerId AND leave_status = 0"
    )
    List<LeaveRequest> getPendingLeaveRequestsByManager(@Param("managerId") Integer manager_id);

    @Modifying
    @Query("UPDATE leave_request SET leave_status = :leave_status WHERE id = :id")
    void patchLeaveStatus(@Param("id") Integer id, @Param("leave_status") Integer leave_status);
}
