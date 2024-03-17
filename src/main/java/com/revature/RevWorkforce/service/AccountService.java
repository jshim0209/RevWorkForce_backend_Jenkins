package com.revature.RevWorkforce.service;

import com.revature.RevWorkforce.repository.*;
import com.revature.RevWorkforce.dtos.*;
import com.revature.RevWorkforce.entity.*;
import com.revature.RevWorkforce.exception.*;
import com.revature.RevWorkforce.model.AccountDetails;
import com.revature.RevWorkforce.repository.AccountRepository;
import jakarta.persistence.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthenticationService authService;
    @Autowired
    PasswordResetRequestRepository passwordResetRequestRepository;
    @PersistenceContext
    private EntityManager entityManager;

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
        return accountRepository.findAllAccounts();
    }

    /**
     * Gets all accounts of type "manager" from the application's account database
     *
     * If there are no manager accounts, then an empty list is returned.
     *
     * @return a list containing every manager Account in the account database
     */
    public List<Account> getAllManagerAccounts() {
        return accountRepository.findAccountsByAccount_type("manager");
    }

    /**
     * Adds an account to the application's account database
     *
     * @param inputAccount the Account to store, without its account_id set
     * @return the same Account with its account_id set to an automatically-generated value
     */
    public Account addAccount(AccountWithoutHash inputAccount) {
        if (inputAccount.getFirstName() == null) {
            throw new InvalidAccountCreationDataException("The first name field cannot be null.");
        }
        else if (inputAccount.getFirstName().isEmpty()) {
            throw new InvalidAccountCreationDataException("The first name field cannot be empty.");
        }
        else if (inputAccount.getFirstName().length() > 64) {
            throw new InvalidAccountCreationDataException("The first name field cannot be larger than 64 characters.");
        }

        if (inputAccount.getLastName() == null) {
            throw new InvalidAccountCreationDataException("The last name field cannot be null.");
        }
        else if (inputAccount.getLastName().isEmpty()) {
            throw new InvalidAccountCreationDataException("The last name field cannot be empty.");
        }
        else if (inputAccount.getLastName().length() > 64) {
            throw new InvalidAccountCreationDataException("The last name field cannot be larger than 64 characters.");
        }

        if (inputAccount.getEmail() == null) {
            throw new InvalidAccountCreationDataException("The email field cannot be null.");
        }
        else if (inputAccount.getEmail().isEmpty()) {
            throw new InvalidAccountCreationDataException("The email field cannot be empty.");
        }
        else if (inputAccount.getEmail().length() > 64) {
            throw new InvalidAccountCreationDataException("The email field cannot be larger than 64 characters.");
        }

        if (!isEmailInputValid(inputAccount.getEmail())) {
            throw new InvalidAccountCreationDataException("The email field does not contain a valid email.");
        }

        if (getAccountByEmail(inputAccount.getEmail()) != null) {
            throw new EmailInUseException("There is already an account with the provided email.");
        }

        if (inputAccount.getPassword() == null) {
            throw new InvalidAccountCreationDataException("The password field cannot be null.");
        }
        else if (inputAccount.getPassword().length() < 4) {
            throw new InvalidAccountCreationDataException("Password must be at least 4 characters long.");
        }

        byte[] encryptedPassword = this.authService.encryptPassword(inputAccount.getPassword());

        if (inputAccount.getPhoneNumber() == null) {
            throw new InvalidAccountCreationDataException("The phone number field cannot be null.");
        }

        String normalizedPhoneNumber = validateAndGetNormalizedPhoneNumber(inputAccount.getPhoneNumber());

        if (normalizedPhoneNumber == null) {
            throw new InvalidAccountCreationDataException("The phone number field does not contain a valid phone number.");
        }

        inputAccount.setPhoneNumber(normalizedPhoneNumber);

        if (inputAccount.getAddress() == null) {
            throw new InvalidAccountCreationDataException("The address field cannot be null.");
        }
        else if (inputAccount.getAddress().isEmpty()) {
            throw new InvalidAccountCreationDataException("The address field cannot be empty.");
        }
        else if (inputAccount.getAddress().length() > 128) {
            throw new InvalidAccountCreationDataException("The address field cannot be larger than 128 characters.");
        }

        String accountType = inputAccount.getAccountType();

        if (accountType == null) {
            throw new InvalidAccountCreationDataException("The account type field cannot be null.");
        }
        else if (!(accountType.equals("employee") || accountType.equals("manager") || accountType.equals("admin"))) {
            throw new InvalidAccountCreationDataException("The account type field must be either employee, manager, or admin.");
        }

        if (inputAccount.getManagerId() != null && inputAccount.getManagerId() < 0) {
            throw new InvalidAccountCreationDataException("The manager ID field must be null or greater than or equal to 0.");
        }

        if (inputAccount.getBirthday() == null) {
            throw new InvalidAccountCreationDataException("The birthday field cannot be null.");
        }
        else if (inputAccount.getFirstWorkday() == null) {
            throw new InvalidAccountCreationDataException("The first workday field cannot be null.");
        }
        else if (inputAccount.getBirthday().compareTo(inputAccount.getFirstWorkday()) > 0) {
            throw new InvalidAccountCreationDataException("The birthday cannot be after the first workday.");
        }

        Account inputAccountWithHash = new Account (
                inputAccount.getFirstName(),
                inputAccount.getLastName(),
                inputAccount.getEmail(),
                encryptedPassword,
                inputAccount.getPhoneNumber(),
                inputAccount.getAddress(),
                inputAccount.getAccountType(),
                inputAccount.getManagerId(),
                inputAccount.getBirthday(),
                inputAccount.getFirstWorkday());

        return accountRepository.save(inputAccountWithHash);
    }

    /*
    An email is considered valid if it meets the following criteria:

    1.) It contains an '@' character
    2.) It contains a '.' character after the '@' character
    3.) There is at least one alphanumeric character (or hyphen) in-between the '@' and '.'
    4.) There is at least one alphanumeric character (or hyphen) before the '@'
    5.) There is at least one alphanumeric character (or hyphen) after the '.'

    Examples of valid emails:
    test@example.com
    t3s5@ExAmPle.c-Om
    3@4.9
    */
    private boolean isEmailInputValid(String input) {
        // According to the above specifications, a valid email could not possibly be shorter than 5 characters long
        if (input.length() < 5) {
            return false;
        }

        char[] validCharacterArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', '-'};

        String lowerCaseInput = input.toLowerCase();

        int atCharPosition = -1;
        int periodCharPosition = -1;
        boolean isCurrentCharValid;

        for (int currentCharIndex = 0; currentCharIndex < lowerCaseInput.length(); currentCharIndex++) {
            // Determine if the current character is the one and only '@'
            if (lowerCaseInput.charAt(currentCharIndex) == '@') {
                if (atCharPosition != -1) {
                    return false;
                }

                atCharPosition = currentCharIndex;
                continue;
            }

            // Determine if the current character is the one and only '.'
            if (lowerCaseInput.charAt(currentCharIndex) == '.') {
                if (periodCharPosition != -1) {
                    return false;
                }

                periodCharPosition = currentCharIndex;
                continue;
            }

            // Determine if the current character is an alphanumeric character
            isCurrentCharValid = false;

            for (int currentValidCharIndex = 0; currentValidCharIndex < validCharacterArray.length; currentValidCharIndex++) {
                if (lowerCaseInput.charAt(currentCharIndex) == validCharacterArray[currentValidCharIndex]) {
                    isCurrentCharValid = true;
                    break;
                }
            }

            if (!isCurrentCharValid) {
                return false;
            }
        }

        // If the input has no '@' character
        if (atCharPosition == -1) {
            return false;
        }

        // If the input has no '.' character
        if (periodCharPosition == -1) {
            return false;
        }

        /*
        Verify that the '.' character is at least 2 characters after the '@' character

        This guarantees both that there is at least 1 valid character between the two,
        and that the '.' character is after the '@' character.
        */
        if (periodCharPosition - atCharPosition < 2) {
            return false;
        }

        // If the '@' character is at position 0, then there cannot possibly any valid characters before it
        if (atCharPosition == 0) {
            return false;
        }

        // If the '.' character is at the end of the string, then there cannot possibly be any valid characters after it
        if (periodCharPosition == (lowerCaseInput.length() - 1)) {
            return false;
        }

        return true;
    }

    /*
    Returns a normalized phone number String from a phone number String if
    the phone number String is considered valid. A normalized phone number
    String is a string composed only of numerals.

    If the phone number is invalid, then null is returned.

    A phone number is considered valid if it meets the following criteria:

    There are between 10 and 13 numbers (if there are 11-13 numbers, then
    the first 1-3 are the country code).

    The area code (the 3 numbers before the last 7) may optionally be surrounded
    by parenthesis. If there are parenthesis, they must form an enclosed pair.

    Between the country code, area code, and between the 4th and 5th-to-last
    numbers, there may be either a space, hyphen, or nothing.

    Examples of valid phone numbers:
    1234567890
    132-(914)-057-7768
    13948-874 7123
    */
    private String validateAndGetNormalizedPhoneNumber(String input) {
        int currentCharValidationIndex = input.length() - 1;
        char[] numeralArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean isCurrentCharNumeral;

        // Make sure that the last 4 characters are numerals
        if (currentCharValidationIndex < 4) {
            return null;
        }

        try {
            // Make sure to avoid allowing negative numbers (unwanted hyphen characters)
            if (Integer.parseInt(input.substring(currentCharValidationIndex - 3, currentCharValidationIndex + 1)) < 0) {
                return null;
            }
        }
        catch (NumberFormatException e) {
            return null;
        }

        currentCharValidationIndex -= 4;

        char[] validCharArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '-'};

        boolean isCurrentCharValid = false;
        isCurrentCharNumeral = false;

        // Make sure that the next character is either numeric, a hyphen, or a space
        for (int currentCharIndex = 0; currentCharIndex < validCharArray.length; currentCharIndex++) {
            if (input.charAt(currentCharValidationIndex) == validCharArray[currentCharIndex]) {
                isCurrentCharValid = true;

                if (currentCharIndex <= 9) {
                    isCurrentCharNumeral = true;
                }

                break;
            }
        }

        if (!isCurrentCharValid) {
            return null;
        }

        if (!isCurrentCharNumeral) {
            currentCharValidationIndex--;

            if (currentCharValidationIndex < 0) {
                return null;
            }
        }

        // Make sure that the next 3 characters are numerals
        if (currentCharValidationIndex < 3) {
            return null;
        }

        try {
            // Make sure to avoid allowing negative numbers (unwanted hyphen characters)
            if (Integer.parseInt(input.substring(currentCharValidationIndex - 2, currentCharValidationIndex + 1)) < 0) {
                return null;
            }
        }
        catch (NumberFormatException e) {
            return null;
        }

        currentCharValidationIndex -= 3;

        validCharArray = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '-', ')'};

        isCurrentCharValid = false;
        isCurrentCharNumeral = false;
        boolean isParenthesisPresent = false;

        // Make sure that the next character is either numeric, a hyphen, a space, or a closing parentheses
        for (int currentCharIndex = 0; currentCharIndex < validCharArray.length; currentCharIndex++) {
            if (input.charAt(currentCharValidationIndex) == validCharArray[currentCharIndex]) {
                isCurrentCharValid = true;

                if (currentCharIndex <= 9) {
                    isCurrentCharNumeral = true;
                }
                else if (currentCharIndex == 12) {
                    isParenthesisPresent = true;
                }

                break;
            }
        }

        if (!isCurrentCharValid) {
            return null;
        }

        if (!isCurrentCharNumeral) {
            currentCharValidationIndex--;

            if (currentCharValidationIndex < 0) {
                return null;
            }

            if (!isParenthesisPresent) {
                // Check to see if there is a closing parentheses after the hyphen or space
                if (input.charAt(currentCharValidationIndex) == ')') {
                    isParenthesisPresent = true;

                    currentCharValidationIndex--;

                    if (currentCharValidationIndex < 0) {
                        return null;
                    }
                }
            }
        }

        // Make sure that the next 3 characters are numerals (area code)
        if (currentCharValidationIndex < 2) {
            return null;
        }

        try {
            // Make sure to avoid allowing negative numbers (unwanted hyphen characters)
            if (Integer.parseInt(input.substring(currentCharValidationIndex - 2, currentCharValidationIndex + 1)) < 0) {
                return null;
            }
        }
        catch (NumberFormatException e) {
            return null;
        }

        currentCharValidationIndex -= 3;

        if (currentCharValidationIndex < 0) {
            if (!isParenthesisPresent) {
                // The phone number is valid - there are no parenthesis around the area code, and there is no country code
                return getNormalizedPhoneNumber(input);
            }
            else {
                return null;
            }
        }

        if (isParenthesisPresent) {
            // Check for the opening parentheses
            if (input.charAt(currentCharValidationIndex) != '(') {
                return null;
            }

            currentCharValidationIndex--;
        }

        if (currentCharValidationIndex < 0) {
            // The phone number is valid - there are parentheses around the area code, and there is no country code
            return getNormalizedPhoneNumber(input);
        }

        validCharArray = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '-'};

        isCurrentCharValid = false;
        isCurrentCharNumeral = false;

        // Make sure that the next character is either numeric, a hyphen, or a space
        for (int currentCharIndex = 0; currentCharIndex < validCharArray.length; currentCharIndex++) {
            if (input.charAt(currentCharValidationIndex) == validCharArray[currentCharIndex]) {
                isCurrentCharValid = true;

                if (currentCharIndex <= 9) {
                    isCurrentCharNumeral = true;
                }

                break;
            }
        }

        if (!isCurrentCharValid) {
            return null;
        }

        if (!isCurrentCharNumeral) {
            currentCharValidationIndex--;

            if (currentCharValidationIndex < 0) {
                return null;
            }
        }

        // Make sure that the next 1-3 characters are numerals (country code)
        if (currentCharValidationIndex > 2) {
            // There should be no more than 3 characters remaining
            return null;
        }

        try {
            // Make sure to avoid allowing negative numbers (unwanted hyphen characters)
            if (Integer.parseInt(input.substring(0, currentCharValidationIndex + 1)) < 0) {
                return null;
            }
        }
        catch (NumberFormatException e) {
            return null;
        }

        // The phone number is valid - the final (leftmost) number in the country code has been validated
        return getNormalizedPhoneNumber(input);
    }

    /*
    Returns a string that contains only the characters in "input" that are numerals
    */
    private String getNormalizedPhoneNumber(String input) {
        StringBuilder normalizedPhoneNumberBuilder = new StringBuilder();

        char[] numeralArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean isCurrentCharNumeral;

        for (int currentCharIndex = 0; currentCharIndex < input.length(); currentCharIndex++) {
            isCurrentCharNumeral = false;

            for (int currentNumeralIndex = 0; currentNumeralIndex < numeralArray.length; currentNumeralIndex++) {
                if (input.charAt(currentCharIndex) == numeralArray[currentNumeralIndex]) {
                    isCurrentCharNumeral = true;
                    break;
                }
            }

            if (isCurrentCharNumeral) {
                normalizedPhoneNumberBuilder.append(input.charAt(currentCharIndex));
            }
        }

        return normalizedPhoneNumberBuilder.toString();
    }

    /**
     * Gets the account with a matching email from the application's account database
     *
     * This will fail if a matching account is not found.
     *
     * @param email the email to look for
     * @return the Account with a matching email
     */
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account authenticateUser(AccountDetails accountDetails) throws InvalidCredentials {
        Account possibleAccount = accountRepository.findByEmail(accountDetails.getEmail());

        if (possibleAccount == null) {
            throw new InvalidCredentials();
        }

        boolean isValid = this.authService.validatePassword(accountDetails.getPassword(), possibleAccount.getPassword());

        if (!isValid) {
            throw new InvalidCredentials();
        }

        entityManager.detach(possibleAccount);

        // Remove password to better secure it.
        possibleAccount.setPassword(null);
        return possibleAccount;
    }
    
    /**
     * Gets all password reset requests from the application's database
     *
     * If there are no password reset requests, then an empty list is returned.
     *
     * @return a list containing every PasswordResetRequest in the account database
     */
    public List<PasswordResetRequest> getAllPasswordResetRequests() {
        return passwordResetRequestRepository.findAllPasswordResetRequests();
    }

    /**
     * Attempts to add a password reset request to the application's password reset request database
     *
     * If the email is in use and does not already have a password reset request,
     * stores the request returns the requested email
     *
     * If the email is not in use or if a password reset request already exists for the email,
     * does not store the request and returns null
     *
     * @param inputEmail the inputEmail to set a password reset request for
     * @return the same inputEmail if the request is stored, or null if the request is not stored
     */
    public String addPasswordResetRequest(String inputEmail) {
        System.out.println("A password reset request has been received for the following email: " + inputEmail);

        Account returnedAccount = getAccountByEmail(inputEmail);
        PasswordResetRequest returnedPasswordResetRequest = passwordResetRequestRepository.findByEmail(inputEmail);

        if (returnedAccount == null) {
            System.out.println("There was no account associated with the email sent in the password reset request.");
            return null;
        }
        else if (returnedPasswordResetRequest != null) {
            System.out.println("There is already a password reset request present for that email.");
            return null;
        }
        else {
            passwordResetRequestRepository.save(new PasswordResetRequest(inputEmail));
            System.out.println("The password reset request was successfully stored.");
            return returnedAccount.getEmail();
        }
    }

    private Account getAccountById(Integer accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if (optionalAccount.isEmpty()) {
            throw new AccountNotFound("Account with account id of " + accountId + " doesn't exist");
        }
        return optionalAccount.get();
    }

    private String getOrDefault(String newValue, String defaultValue) {
        return !newValue.isEmpty() && Objects.nonNull(newValue) ? newValue : defaultValue;
    }

    public ProfileResponseDto updateAccountProfile(Integer accountId, UpdateProfileRequestDto profileRequestDto) {
        Account accountToUpdate = getAccountById(accountId);

        accountToUpdate.setEmail(getOrDefault(profileRequestDto.getEmail(), accountToUpdate.getEmail()));
        accountToUpdate.setFirstName(getOrDefault(profileRequestDto.getFirstName(), accountToUpdate.getFirstName()));
        accountToUpdate.setLastName(getOrDefault(profileRequestDto.getLastName(), accountToUpdate.getLastName()));
        accountToUpdate.setPhoneNumber(getOrDefault(profileRequestDto.getPhoneNumber(), accountToUpdate.getPhoneNumber()));
        accountToUpdate.setAddress(getOrDefault(profileRequestDto.getAddress(), accountToUpdate.getAddress()));
        accountToUpdate.setBirthday(Objects.nonNull(profileRequestDto.getBirthday()) ? profileRequestDto.getBirthday() : accountToUpdate.getBirthday());

        return modelMapper.map(accountRepository.saveAndFlush(accountToUpdate), ProfileResponseDto.class);
    }

    public ProfileResponseDto getProfileByAccountId(Integer accountId) {
        return modelMapper.map(getAccountById(accountId), ProfileResponseDto.class);
    }

    public Account getAccountByAccountId(Integer account_id) throws AccountNotFound {
        Account account = accountRepository.getAccountByAccountId(account_id);
        if (account == null) {
            throw new AccountNotFound("Account not found");
        }
        return account;
    }

    public String getAccountName(Integer accountId) throws AccountNotFound {
        Account account = accountRepository.getAccountName(accountId);

        if (account == null) {
            throw new AccountNotFound("Account not found");
        }

        return account.getFullName();
    }

    public List<Account> getEmployees(Integer id){
        return accountRepository.getAccountsByManager(id);
    }
}
