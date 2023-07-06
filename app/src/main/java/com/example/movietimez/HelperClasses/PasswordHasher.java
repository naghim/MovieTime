package com.example.movietimez.HelperClasses;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {

    public static String hash(String password) {
        MessageDigest digest = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return null;
        }

        digest.reset();
        return bin2hex(digest.digest(password.getBytes()));
    }

    private static String bin2hex(byte[] data) {
        StringBuilder hex = new StringBuilder(data.length * 2);
        
        for (byte b : data) {
            hex.append(String.format("%02x", b & 0xFF));
        }

        return hex.toString();
    }
}