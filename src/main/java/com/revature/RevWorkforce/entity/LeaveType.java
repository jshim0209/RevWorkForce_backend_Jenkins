package com.revature.RevWorkforce.entity;

import jakarta.persistence.*;

@Entity(name = "leave_type")
@Table(name = "leave_type")
public class LeaveType {
    @Id
    private String type_name;

    public LeaveType() {

    }

    public LeaveType(String type_name) {
        this.type_name = type_name;
    }

    public String getType_name() { return this.type_name; }
    public void setType_name(String type_name) { this.type_name = type_name; }

    @Override
    public String toString() {
        return "LeaveType: { " +
                "typeName: " + type_name + " }";
    }
}