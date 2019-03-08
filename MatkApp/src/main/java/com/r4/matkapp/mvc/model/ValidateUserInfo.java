/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.model;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


/**
 *
 * @author Eero
 */
public class ValidateUserInfo {
    static final int MIN_PASSWORD_LENGTH = 4;
    static final int MAX_PASSWORD_LENGTH = 14;
    
    public static boolean isValid(String first_name, String last_name, String email, String password) {
        
        // Check not empty.
        if(first_name.equals("")
                || last_name.equals("")
                || email.equals("")
                || password.equals("")) {
            return false;
        }
        
        // Check password length.
        if(password.length() < MIN_PASSWORD_LENGTH 
                || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }
        
        
        // Validate email address.
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
        } catch (AddressException ex) {
            return false;
        }
        
        // All good m8
        return true;    
    }
}
