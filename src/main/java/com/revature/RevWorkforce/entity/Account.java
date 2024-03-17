package com.revature.RevWorkforce.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "account")
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private byte[] password;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    @Column(name = "account_type")
    private String accountType;
    private LocalDate birthday;
    @Column(name = "first_workday")
    private LocalDate firstWorkday;
    @Column(name = "managerId")
    private Integer managerId;

    public Account(String firstName,
                   String lastName,
                   String email,
                   byte[] password,
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

    public Integer getAccountId() { return this.accountId; }
    public Integer getManagerId() { return this.managerId; }

    public String getFullName() { return this.firstName + " " + this.lastName; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Account account2 = (Account) obj;

        if (accountId == null && account2.accountId != null) {
            return false;
        }
        else if (!accountId.equals(account2.accountId)) {
            return false;
        }

        if (firstName == null && account2.firstName != null) {
            return false;
        }
        else if (!firstName.equals(account2.firstName)) {
            return false;
        }

        if (lastName == null && account2.lastName != null) {
            return false;
        }
        else if (!lastName.equals(account2.lastName)) {
            return false;
        }

        if (email == null && account2.email != null) {
            return false;
        }
        else if (!email.equals(account2.email)) {
            return false;
        }

        if (password == null && account2.password != null) {
            return false;
        }
        else if (!Arrays.equals(password, account2.password)) {
            return false;
        }

        if (phoneNumber == null && account2.phoneNumber != null) {
            return false;
        }
        else if (!phoneNumber.equals(account2.phoneNumber)) {
            return false;
        }

        if (address == null && account2.address != null) {
            return false;
        }
        else if (!address.equals(account2.address)) {
            return false;
        }

        if (accountType == null && account2.accountType != null) {
            return false;
        }
        else if (!accountType.equals(account2.accountType)) {
            return false;
        }

        if (managerId == null && account2.managerId != null) {
            return false;
        }
        else if (!managerId.equals(account2.managerId)) {
            return false;
        }

        if (birthday == null && account2.birthday != null) {
            return false;
        }
        else if (!birthday.equals(account2.birthday)) {
            return false;
        }

        if (firstWorkday == null && account2.firstWorkday != null) {
            return false;
        }
        else if (!firstWorkday.equals(account2.firstWorkday)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + accountId +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password=" + binaryToString(password) +
                ", phone_number='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", account_type='" + accountType + '\'' +
                ", manager_id=" + managerId +
                ", birthday='" + birthday.toString() + '\'' +
                ", first_workday='" + firstWorkday.toString() + '\'' +
                '}';
    }

    private String binaryToString(byte[] inputBinary) {
        StringBuilder outputStringBuilder = new StringBuilder((inputBinary.length * 2) + 2);

        outputStringBuilder.append("0x");

        int currentUnsignedValue;

        for (byte currentByte : inputBinary) {
            if (currentByte >= 0) {
                currentUnsignedValue = currentByte;
            }
            else {
                // Convert the current byte into an unsigned representation
                currentUnsignedValue = 256 + currentByte;
            }

            outputStringBuilder.append(intToHexadecimalDigit(currentUnsignedValue >> 4)); // The most significant hex digit
            outputStringBuilder.append(intToHexadecimalDigit(currentUnsignedValue & 0xf)); // The least significant hex digit
        }

        return outputStringBuilder.toString();
    }

    private char intToHexadecimalDigit(int inputInteger) {
        // ASCII and Unicode numerals are 48 higher than the integer they are representing
        if (inputInteger >= 0 && inputInteger <= 9) {
            return (char) (inputInteger + 48);
        }
        // ASCII and Unicode lowercase hexadecimal equivalents are 87 higher than the integer they are representing
        else if (inputInteger >= 10 && inputInteger <= 15) {
            return (char) (inputInteger + 87);
        }
        else {
            throw new IllegalArgumentException("Input integers must be in the range 0-15 inclusive.");
        }
    }


}
