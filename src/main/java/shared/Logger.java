package shared;

import java.time.LocalTime;
import java.util.Arrays;

/**
 * <h1>Message Logger</h1>
 * <p>This class will serve as a general purpose system logger for messages and errors</p>
 */
public class Logger {
    private Logger() {}

    /**
     * This method will add a {@code String} message to the logger, with a timestamp of the event
     * @param s The message that will be added to the application log
     */
    public static void log(String s) {
        System.out.println("[" + LocalTime.now() + "] >> " + s);
    }

    /**
     * This method will add to the log the exception name and its stack trace
     * @param e An {@code Exception} object to obtain its name and stack trace
     */
    public static void strace(Exception e) {
        System.out.println("\n[Stack trace for " + e.toString() + "]");
        Arrays.stream(e.getStackTrace()).forEach(System.out::println);
    }
}