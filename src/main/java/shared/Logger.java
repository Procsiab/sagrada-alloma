package shared;

import org.fusesource.jansi.AnsiConsole;

import java.time.LocalTime;
import java.util.Arrays;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * <h1>Message Logger</h1>
 * <p>This class will serve as a general purpose system logger for messages and errors</p>
 */
public class Logger {
    private Logger() {}

    /**
     * This method will addDice a {@code String} message to the logger, with a timestamp of the event
     * @param s The message that will be added to the application log
     */
    public static void  log(Object s) {
        System.out.println("[" + LocalTime.now() + "] >> \t" + s.toString());
    }

    /**
     * This method will addDice to the log the exception name and its stack trace
     * @param e An {@code Exception} object to obtain its name and stack trace
     */
    public static void strace(Exception e) {
        System.out.println("\n[Stack trace for " + e.toString() + "]");
        Arrays.stream(e.getStackTrace()).forEach(System.out::println);
        System.out.print("\n\n");
    }

    /**
     * This method will addDice to the log the exception name and its stack trace
     * @param e An {@code Exception} object to obtain its name and stack trace
     * @param evil Choose whether the stack trace should be logged in magenta ({@code true} | {@code false})
     */
    public static void strace(Exception e, boolean evil) {
        if (evil) {
            AnsiConsole.out().print(ansi().fgBrightMagenta());
            strace(e);
            AnsiConsole.out().print(ansi().fgDefault());
        }
    }
}