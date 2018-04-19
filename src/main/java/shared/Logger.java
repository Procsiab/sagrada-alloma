package shared;

import java.time.LocalDateTime;

public class Logger {
    private Logger() {}

    public static void log(String s) {
        System.out.println("[" + LocalDateTime.now() + "] >> " + s);
    }
}
