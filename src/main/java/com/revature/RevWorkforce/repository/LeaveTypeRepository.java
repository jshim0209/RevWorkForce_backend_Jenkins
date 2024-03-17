package com.revature.RevWorkforce.repository;

import com.revature.RevWorkforce.entity.LeaveType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Integer> {
    @Query("FROM leave_type WHERE type_name = :typeName")
    LeaveType getLeaveTypeByName(@Param("typeName") String typeName);
}
