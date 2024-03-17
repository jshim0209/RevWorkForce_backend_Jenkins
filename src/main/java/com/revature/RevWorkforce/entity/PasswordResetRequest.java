package com.revature.RevWorkforce.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "password_reset_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer requestId;
    @Column(nullable = false)
    private String email;

    public PasswordResetRequest(String email) {
        this.email = email;
    }

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

        PasswordResetRequest passwordResetRequest2 = (PasswordResetRequest) obj;

        if (requestId == null && passwordResetRequest2.requestId != null) {
            return false;
        }
        else if (!requestId.equals(passwordResetRequest2.requestId)) {
            return false;
        }

        if (email == null && passwordResetRequest2.email != null) {
            return false;
        }
        else if (!email.equals(passwordResetRequest2.email)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "PasswordResetRequest{" +
                "id=" + requestId +
                ", email='" + email + '\'' +
                '}';
    }
}
