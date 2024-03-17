package com.revature.RevWorkforce.model;

public class AccountDetails {
    private String email;
    private String password;

    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }

    @Override
    public String toString() {
        return "AccountDetails: { " +
                "email: " + email +
                ", password: " + password + " }";
    }
}
