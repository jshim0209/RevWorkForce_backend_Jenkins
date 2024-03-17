package com.revature.RevWorkforce.repository;

import com.revature.RevWorkforce.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave,Long> {

    public List<Leave> findByAccountId(int id);

}
