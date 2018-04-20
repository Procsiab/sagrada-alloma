package shared;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Logger {
    private Logger() {}

    public static void log(String s) {
        System.out.println("[" + LocalDateTime.now() + "] >> " + s);
    }

    public static void strace(Exception e) {
        System.out.println("[Stack trace]\n" + Arrays.toString(e.getStackTrace()));
    }
}