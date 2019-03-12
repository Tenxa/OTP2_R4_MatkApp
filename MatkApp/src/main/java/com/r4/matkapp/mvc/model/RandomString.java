/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.model;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * Class for generating random string containing upper and lower case alphabets
 * and digits.
 *
 * @author Eero
 */
public class RandomString {
    
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase(Locale.ROOT);
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;

    private final Random random;
    private final char[] symbols;
    private final char[] buf;
    
      
    /**
     * Creates random String containing alphabets and digits based on given length.
     * @param length 
     */
    public RandomString(int length) {
        Random random = new SecureRandom();
        String symbols = alphanum;
        
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }
    
    /**
     * Fetch next String from buffer.
     * @return 
     */
    public String nextString() {
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }
  
}
