package com.r4.matkapp.mvc.model;

import com.r4.matkapp.mvc.model.User;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Class for generating PBKDF2 hash from String and
 * authenticating user.
 * @author Mika
 */

// TODO:
// - tesit

// Each user has a unique salt
// This salt must be recomputed during password change!
// (salt = random string, joka lisätään salasanan perään
// krytauksen randomisoinnin varimistamiseksi)

public class SecurePassword {
    
    /**
     * Authenticates User info by comparing email and encrypted password.
     * @param user
     * @param inputPass
     * @return
     * @throws Exception 
     */
    public boolean authenticateUser(User user, String inputPass) throws Exception {
        if (user == null) {
            return false;
        } else {
            String salt = user.getSalt();
            String calculatedHash = getEncryptedPassword(inputPass, salt);
            if (calculatedHash.equals(user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    /**
     * Generates Encrypted password for password with given salt.
     * @param inputPassword
     * @param salt
     * @return
     * @throws Exception 
     */
    public String generateEncryptedPassword(String inputPassword, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160;
        int iterations = 20000;
        
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(inputPassword.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
        
        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }
    
    /**
     * Get a encrypted password using PBKDF2 hash algorithm
     */
    public String getEncryptedPassword(String password, String salt) throws Exception {
        String algorithm = "PBKDF2WithHmacSHA1";
        int derivedKeyLength = 160; // for SHA1
        int iterations = 20000; // NIST specifies 10000

        byte[] saltBytes = Base64.getDecoder().decode(salt);
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, derivedKeyLength);
        SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

        byte[] encBytes = f.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(encBytes);
    }

    
    /**
     * Returns base64 encoded salt.
     * @return
     * @throws Exception 
     */
    // (salt = random string, joka lisätään salasanan perään
    // kryptauksen randomisoinnin varimistamiseksi)
    public String getNewSalt() throws Exception {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        // NIST recommends minimum 4 bytes. We use 8.
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

}



