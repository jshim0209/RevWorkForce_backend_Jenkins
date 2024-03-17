package com.revature.RevWorkforce.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class AuthenticationService {
    public byte[] encryptPassword(String input) {
        try {
            MessageDigest digestCalculator = MessageDigest.getInstance("SHA-256");
            digestCalculator.update(input.getBytes());
            return digestCalculator.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean validatePassword(String input, byte[] actual) {
        try {
            MessageDigest digestCalculator = MessageDigest.getInstance("SHA-256");
            digestCalculator.update(input.getBytes());
            return Arrays.equals(digestCalculator.digest(), actual);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }
}
