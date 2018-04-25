package shared;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

public class Logger {
    private Logger() {}

    public static void log(String s) {
        System.out.println("[" + LocalTime.now() + "] >> " + s);
    }

    public static void strace(Exception e) {
        System.out.println("\n[Stack trace for " + e.toString() + "]");
        Arrays.stream(e.getStackTrace()).forEach(System.out::println);
    }
}