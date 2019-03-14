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
public class RandomStringTest {
    
    RandomString rString;
    
    /**
     * Test of nextString method, of class RandomString.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentException() {    
        rString = new RandomString(0);
    }
    
}
