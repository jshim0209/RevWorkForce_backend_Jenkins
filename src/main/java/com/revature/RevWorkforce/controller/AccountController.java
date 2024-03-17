package com.revature.RevWorkforce.controller;

import com.revature.RevWorkforce.dtos.*;
import com.revature.RevWorkforce.entity.*;
import com.revature.RevWorkforce.exception.*;
import com.revature.RevWorkforce.model.AccountDetails;
import com.revature.RevWorkforce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping(value = "/accounts")
    /**
     * Gets all accounts from the application's account database
     *
     * If there are no accounts, then an empty list is returned.
     *
     * If successful, sets the HTTP status to 200
     *
     * @return a list containing every Account in the account database
     */
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{accountId}")
    public ProfileResponseDto getProfileByAccountId(@PathVariable Integer accountId) {
        return accountService.getProfileByAccountId(accountId);
    }

    @GetMapping("/accounts/name/{accountId}")
    public ResponseEntity<String> getAccountName(@PathVariable Integer accountId) {
        try {
            String accountName = accountService.getAccountName(accountId);
            return ResponseEntity.ok(accountName);
        } catch (AccountNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PatchMapping("/accounts/{accountId}")
    public ProfileResponseDto updateProfile(@PathVariable Integer accountId,
                                            @RequestBody UpdateProfileRequestDto profileRequestDto) {
        return accountService.updateAccountProfile(accountId, profileRequestDto);
    }

    @GetMapping("/users")
    public ResponseEntity<Account> getByEmail(@RequestParam String email){
        Account user = accountService.getAccountByEmail(email);
        if(user != null){
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/login")
    public Account loginUser(@RequestBody AccountDetails accountDetails) throws InvalidCredentials {
        return accountService.authenticateUser(accountDetails);
    }

    @GetMapping(value = "/accounts/managers")
    /**
     * Gets all accounts of type "manager" from the application's account database
     *
     * If there are no manager accounts, then an empty list is returned.
     *
     * If successful, sets the HTTP status to 200
     *
     * @return a list containing every manager Account in the account database
     */
    public List<Account> getAllManagerAccounts() {
        return accountService.getAllManagerAccounts();
    }

    @PostMapping(value = "/account")
    /**
     * Attempts to add an account to the application's account database
     *
     * If successful, sets the HTTP status to 200
     *
     * @param inputAccount the Account to store, without its account_id set
     * @return the same Account with its account_id set to an automatically-generated value
     */
    public Account addAccount(@RequestBody AccountWithoutHash inputAccount) {
        return accountService.addAccount(inputAccount);
    }

    @GetMapping(value = "/resets")
    /**
     * Gets all password reset requests from the application's database
     *
     * If there are no password reset requests, then an empty list is returned.
     *
     * If successful, sets the HTTP status to 200
     *
     * @return a list containing every PasswordResetRequest in the account database
     */
    public ResponseEntity<List<PasswordResetRequest>> getAllPasswordResetRequest() {
        return ResponseEntity.ok(accountService.getAllPasswordResetRequests());
    }

    @PostMapping(value = "/resets")
    /**
     * Attempts to add a password reset request to the application's password reset request database
     *
     * If the email is in use and does not already have a password reset request,
     * sets the HTTP status to 200 and returns the same requested email
     *
     * If the email is not in use or if a password reset request already exists for the email,
     * sets the HTTP status to 404 and returns null
     *
     * @param inputEmail the inputEmail to set a password reset request for
     * @return the same inputEmail, or null if inputEmail is not in use by an account
     */
    public ResponseEntity<String> addPasswordResetRequest(@RequestBody String inputEmail) {
        String returnedEmail = accountService.addPasswordResetRequest(inputEmail);

        if (returnedEmail != null) {
            return ResponseEntity.ok(returnedEmail);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
      
    @GetMapping("/manager/{account_id}")
    public ResponseEntity<List<Account>> getEmployees(@PathVariable Integer account_id) {
        return ResponseEntity.ok(accountService.getEmployees(account_id));
    }
}
