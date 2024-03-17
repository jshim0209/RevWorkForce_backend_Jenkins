package com.revature.RevWorkforce.controller;

import com.revature.RevWorkforce.entity.*;
import com.revature.RevWorkforce.exception.*;
import com.revature.RevWorkforce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
public class LeaveController {
    @Autowired
    LeaveService leaveService;
    @Autowired
    LeaveRequestService leaveRequestService;
    @Autowired
    LeaveTypeService leaveTypeService;

    @GetMapping("/{account_id}")
    public List<Leave> getLeaves(@PathVariable Integer account_id){
        return this.leaveService.getLeaves(account_id);
    }

    @PostMapping (value = "/apply", consumes = "application/json")
    public void employeeApplyForLeave(@RequestBody LeaveRequest leaveRequest)
            throws LeaveRequestMissingInformation, LeaveDateRangeTooLarge, LeaveDateMismatch, LeaveTypeNotFound
    {
        this.leaveRequestService.addLeaveApplication(leaveRequest);
    }

    @GetMapping("/types")
    public List<LeaveType> getLeaveTypes() {
        return this.leaveTypeService.getLeaveTypes();
    }

    @GetMapping("/requests/{account_id}")
    public List<LeaveRequest> getLeaveRequestsByAccountId(@PathVariable("account_id") Integer account_id) {
        return this.leaveRequestService.getEmployeeLeaveRequests(account_id);
    }

    @PatchMapping("/{leave_id}")
    public LeaveRequest updateLeaveStatus(@PathVariable Integer leave_id,
                                          @RequestParam Integer leave_status) {
        return leaveRequestService.updateLeaveStatus(leave_id, leave_status);
    }

    @GetMapping()
    public List<LeaveRequest> getAllLeaveRequests() {
        return this.leaveRequestService.getAllLeaveRequests();
    }
    
    @GetMapping("/pending/{manager_id}")
    public List<LeaveRequest> getPendingLeaveByManager(@PathVariable("manager_id") Integer manager_id) {
        return this.leaveRequestService.getPendingRequestsByManager(manager_id);
    }

    @PatchMapping("/{id}/status/{leave_status}")
    public ResponseEntity<String> patchLeaveStatus(@PathVariable("id") Integer id, @PathVariable("leave_status") Integer leave_status) {
        if (!leaveRequestService.patchLeaveStatus(id, leave_status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok().body(null);
    }
}