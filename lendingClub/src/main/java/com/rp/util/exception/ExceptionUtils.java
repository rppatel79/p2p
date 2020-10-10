package com.rp.util.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtils {
    /**
     * Private on purpose
     * This class is designed to support only static methods
     */
    private ExceptionUtils() {
    }


    /**
     * Converts a Throwable's stacktrack into a string
     *
     * @param t The throwable
     * @return The stacktrack of the throwable
     */
    public static String getStackTrace(Throwable t) {
        if (t == null)
            return null;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);

        return sw.toString();
    }
}
