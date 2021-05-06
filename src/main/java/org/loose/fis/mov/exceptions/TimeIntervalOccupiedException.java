package org.loose.fis.mov.exceptions;

public class TimeIntervalOccupiedException extends Exception{
    public TimeIntervalOccupiedException() {
        super("This time interval is occupied for another screening!");
    }
}
