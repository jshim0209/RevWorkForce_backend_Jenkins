package com.revature.RevWorkforce.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountWithoutHash {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String accountType;
    private Integer managerId;
    private LocalDate birthday;
    private LocalDate firstWorkday;

    public AccountWithoutHash(String firstName,
                   String lastName,
                   String email,
                   String password,
                   String phoneNumber,
                   String address,
                   String accountType,
                   Integer managerId,
                   LocalDate birthday,
                   LocalDate firstWorkday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.accountType = accountType;
        this.managerId = managerId;
        this.birthday = birthday;
        this.firstWorkday = firstWorkday;
    }

    @Override
    public String toString() {
        return "Account{" +
                "first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password=" + password + '\'' +
                ", phone_number='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", account_type='" + accountType + '\'' +
                ", manager_id=" + managerId +
                ", birthday='" + birthday.toString() + '\'' +
                ", first_workday='" + firstWorkday.toString() + '\'' +
                '}';
    }
}