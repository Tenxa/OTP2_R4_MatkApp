/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.view;

import java.text.DecimalFormat;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

/**
 * The <code>ElementInitor</code> is class for initializing some javafx elements.
 * Currently has only method for {@link javafx.scene.control.Spinner}.
 * @author Eero
 */
public class ElementInitor {
    
    /**
     * Sets new SpinnerValueFactory for Spinner and adds StringConvertrer for 
     * converting input text into double.
     * 
     * @param spinner Spinner to initialize
     */
    public void init(Spinner spinner) {
        SpinnerValueFactory.DoubleSpinnerValueFactory priceFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 999999, 0, 0.1);
        // define converter
        priceFactory.setConverter(new StringConverter<Double>() {
            private final DecimalFormat df = new DecimalFormat("#.##");

            @Override
            public String toString(Double value) {
                // If the specified value is null, return a zero-length String
                if (value == null) {
                    return "";
                }

                return df.format(value);
            }

            @Override
            public Double fromString(String value) {
                try {
                    // If the specified value is null or zero-length, return null
                    if (value == null) {
                        return null;
                    }

                    value = value.trim();

                    if (value.length() < 1) {
                        return null;
                    }

                    // Perform the requested parsing  
                    return df.parse(value).doubleValue();

                } catch (Exception ex) {
                    return 0.0;
                }
            }

        });
        spinner.setValueFactory(priceFactory);
        
        // for removing alphabeths
        TextFormatter formatter = new TextFormatter(priceFactory.getConverter(), priceFactory.getValue());
        spinner.getEditor().setTextFormatter(formatter);
        priceFactory.valueProperty().bindBidirectional(formatter.valueProperty());
        
        // add scroll event
        spinner.setOnScroll(event -> {
            if (event.getDeltaY() < 0d) {
                spinner.decrement();
            } else if (event.getDeltaY() > 0d) {
                spinner.increment();
            }
        });
    }
}
