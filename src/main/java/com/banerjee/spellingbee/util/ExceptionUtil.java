package com.banerjee.spellingbee.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by somobanerjee on 2/26/17.
 */
public class ExceptionUtil {
    public static String getExceptionAsString(Exception ex) {
        try {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();

            return exceptionAsString;
        } catch (Exception e) {
            return null;
        }

    }
}
