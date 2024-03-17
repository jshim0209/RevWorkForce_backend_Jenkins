package com.revature.RevWorkforce.service;

import com.revature.RevWorkforce.entity.LeaveType;
import com.revature.RevWorkforce.repository.LeaveTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class LeaveTypeService {
    LeaveTypeRepository leaveTypeRepository;

    @Autowired
    public LeaveTypeService(LeaveTypeRepository leaveTypeRepository) {
        this.leaveTypeRepository = leaveTypeRepository;
    }

    public List<LeaveType> getLeaveTypes() {
        return this.leaveTypeRepository.findAll();
    }

    public LeaveType getLeaveTypeByName(String type_name) {
        return this.leaveTypeRepository.getLeaveTypeByName(type_name);
    }

}
