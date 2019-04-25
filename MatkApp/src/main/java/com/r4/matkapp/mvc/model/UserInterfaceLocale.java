package com.r4.matkapp.mvc.model;

import java.util.*;

/**
 *
 * @author Mika
 */

public class UserInterfaceLocale {

    //private ResorceBundle messages;
    private final Locale ENLOCALE = new Locale("en", "US");
    private final Locale FILOCALE = new Locale("fi", "FI");
    private final Locale JALOCALE = new Locale("ja", "JP");
    private final Locale DEFAULTLOCALE = ENLOCALE;
    public static Locale currentLocale = new Locale("en", "US");

    //messages  = ResourceBundle.getBundle("javafxapplicaton1.MessagesBundle", DEFAULTLOCALE);

    public void changeLocale(Locale locale) {
        currentLocale = locale;
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }

    public Locale getEnLocale() {
        return ENLOCALE;
    }

    public Locale getFiLocale() {
        return FILOCALE;
    }

    public Locale getJaLocale() {
        return JALOCALE;
    }
}
