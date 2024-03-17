package com.revature.RevWorkforce.service;

import com.revature.RevWorkforce.dtos.ProfileResponseDto;
import com.revature.RevWorkforce.entity.*;
import com.revature.RevWorkforce.exception.*;
import com.revature.RevWorkforce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class LeaveRequestService {
    @Autowired
    LeaveRequestRepository leaveRequestRepository;
    @Autowired
    LeaveTypeService leaveTypeService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    AccountService accountService;

    private LeaveRequest getLeaveRequestById(Integer leave_id) {
        Optional<LeaveRequest> optionalLeaveRequest = leaveRequestRepository.findById(leave_id);
        if (optionalLeaveRequest.isEmpty()) {
            throw new LeaveRequestNotFound("Leave request with " + leave_id + " does not exist!");
        }
        return optionalLeaveRequest.get();
    }

    public void addLeaveApplication(LeaveRequest leaveRequest) throws LeaveRequestMissingInformation, LeaveDateRangeTooLarge, LeaveDateMismatch, LeaveTypeNotFound {
        if (leaveRequest.getLeave_type() == null ||
                leaveRequest.getLeave_type().isEmpty() ||
                leaveRequest.getStart_date() == null ||
                leaveRequest.getEnd_date() == null ||
                leaveRequest.getAccount_id() == null
        ) {
            throw new LeaveRequestMissingInformation();
        }

        if (leaveTypeService.getLeaveTypeByName(leaveRequest.getLeave_type()) == null) {
            throw new LeaveTypeNotFound();
        }

        if (leaveRequest.getStart_date().isAfter(leaveRequest.getEnd_date())) {
           throw new LeaveDateMismatch();
        }

        // Number is the amount of milliseconds in a 14 day period of time.
        if (leaveRequest.getStart_date().until(leaveRequest.getEnd_date()).getDays() >= 14) {
            throw new LeaveDateRangeTooLarge();
        }

        Account account = null;

        try {
            account = accountService.getAccountByAccountId(leaveRequest.getAccount_id());
        } catch (AccountNotFound e) {
            throw e;
        }

        if (account.getManagerId() != null) {
            notificationService.addNotification(
                    account.getManagerId(),
                    account.getFullName() + " has requested leave and is awaiting approval."
            );
        }

        leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequest> getEmployeeLeaveRequests(Integer account_id) {
        return leaveRequestRepository.getLeaveRequestsByAccountId(account_id);
    }

    public LeaveRequest updateLeaveStatus(Integer leave_id, Integer leave_status) {
        LeaveRequest leaveRequestToUpdate = getLeaveRequestById(leave_id);
        leaveRequestToUpdate.setLeave_status(leave_status);
        return leaveRequestRepository.saveAndFlush(leaveRequestToUpdate);
    }
    
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.getAllLeaveRequests();
    }
    
    public List<LeaveRequest> getPendingRequestsByManager(Integer manager_id) {
        return leaveRequestRepository.getPendingLeaveRequestsByManager(manager_id);
    }

    public boolean patchLeaveStatus(Integer id, Integer leave_status) {
        if (leave_status < -1 || leave_status > 1) {
            return false;
        }

        leaveRequestRepository.patchLeaveStatus(id, leave_status);

        return true;
    }
}
