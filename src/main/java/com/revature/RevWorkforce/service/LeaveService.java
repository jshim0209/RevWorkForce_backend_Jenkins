package com.revature.RevWorkforce.service;

import com.revature.RevWorkforce.entity.Leave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.revature.RevWorkforce.repository.LeaveRepository;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    LeaveRepository leaveRepository;

    public List<Leave> getLeaves(int id){
        return leaveRepository.findByAccountId(id);
    }
}
