package org.loose.fis.mov.exceptions;

public class MovieLengthNotUnsignedIntegerException extends Exception {
    public MovieLengthNotUnsignedIntegerException() {
        super("The Movie Length field is not a number!");
    }
}
