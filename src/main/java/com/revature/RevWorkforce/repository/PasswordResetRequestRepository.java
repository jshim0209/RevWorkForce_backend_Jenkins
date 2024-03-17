package com.revature.RevWorkforce.repository;

import com.revature.RevWorkforce.entity.PasswordResetRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, Integer> {
    /**
     * Finds and returns a list of PasswordResetRequest objects in the database
     *
     * @return a list of all PasswordResetRequest objects
     */
    @Query(value = "SELECT * FROM password_reset_request", nativeQuery = true)
    public List<PasswordResetRequest> findAllPasswordResetRequests();

    /**
     * Finds and returns the PasswordResetRequest with a matching email
     *
     * @param email the email of the PasswordResetRequest to find
     * @return the PasswordResetRequest with a matching email
     */
    public PasswordResetRequest findByEmail(String email);
}