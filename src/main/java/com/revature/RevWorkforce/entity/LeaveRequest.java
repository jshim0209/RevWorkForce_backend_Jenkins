package com.revature.RevWorkforce.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name = "leave_request")
@Table(name = "leave_request")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "account_id")
    private Integer account_id;
    private LocalDate start_date;
    private LocalDate end_date;
    private String leave_type;
    private Integer leave_status;

    public LeaveRequest() {

    }

    public LeaveRequest(Integer account_id, LocalDate start_date, LocalDate end_date, String leave_type) {
        this.account_id = account_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.leave_type = leave_type;
    }

    public Integer getId() { return this.id; }
    public Integer getAccount_id() { return this.account_id; }
    public LocalDate getStart_date() { return this.start_date; }
    public LocalDate getEnd_date() { return this.end_date; }
    public String getLeave_type() { return this.leave_type; }
    public Integer getLeave_status() { return this.leave_status; }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setAccount_id(Integer account_id) { this.account_id = account_id; }
    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }
    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }
    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }
    public void setLeave_status(Integer leave_status) { this.leave_status = leave_status; }

    @Override
    public String toString() {
        return "LeaveRequest: { " +
                "id: " + id +
                ", account_id: " + account_id +
                ", start_date: " + start_date.toString() +
                ", end_date: " + end_date.toString() +
                ", leave_type: " + leave_type +
                ", leave_status: " + leave_status + " }";
    }
}
