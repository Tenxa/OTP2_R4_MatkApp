/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eero
 */
public class ValidateUserInfoTest {
    
   
    @Test
    public void testIsValid() {
        String first_name = "Teppo";
        String last_name = "Testi";
        String email = "teppo.testi@gmail.com";
        String password = "teppo420testi";
        boolean expResult = true;
        boolean result = ValidateUserInfo.isValid(first_name, last_name, email, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testNotValidEmail() {
        String first_name = "Teppo";
        String last_name = "Testi";
        String email = "asdfgtedsr";
        String password = "teppo420testi";
        boolean expResult = false;
        boolean result = ValidateUserInfo.isValid(first_name, last_name, email, password);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testTooShortPassword() {
        String first_name = "Teppo";
        String last_name = "Testi";
        String email = "teppo.testi@gmail.com";
        String password = "tap";
        boolean expResult = false;
        boolean result = ValidateUserInfo.isValid(first_name, last_name, email, password);
        assertEquals(expResult, result); 
    }
    
    @Test
    public void testTooLongPassword() {
        String first_name = "Teppo";
        String last_name = "Testi";
        String email = "teppo.testi@gmail.com";
        String password = "teppo123456791011";
        boolean expResult = false;
        boolean result = ValidateUserInfo.isValid(first_name, last_name, email, password);
        assertEquals(expResult, result); 
    }
    
    @Test
    public void testEmptyInput() {
        String first_name = "";
        String last_name = "";
        String email = "";
        String password = "";
        boolean expResult = false;
        boolean result = ValidateUserInfo.isValid(first_name, last_name, email, password);
        assertEquals(expResult, result); 
    }
    
}
