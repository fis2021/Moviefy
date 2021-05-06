package org.loose.fis.mov.exceptions;

public class DateInThePastException extends Exception {
    public DateInThePastException() {
        super("The inputted date is in the past!");
    }
}
