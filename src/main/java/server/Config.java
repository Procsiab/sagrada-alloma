package server;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Config {
    /**
     * respectively,
     * timeout of the GameGenerator
     * timeout for each player to play
     * timeout for initialization of graphics
     * timeout to show server status after its initialization
     * timeout of each real time online check
     * max number of players the server currently handles
     */
    public static Integer timeout1;
    public static Integer timeout2;
    public static Integer timeout3;
    public static Integer timeout4;
    public static Integer timeout5;
    public static Integer timeout6;
    public static Integer maxActivePlayerRefs;


    public static boolean read() {

        ArrayList<Integer> timers = new ArrayList<>();
        File file = new File("config.txt");
        try (Scanner scan = new Scanner(file)) {
            scan.useDelimiter(Pattern.compile("@"));
            while (scan.hasNext()) {
                timers.add(Integer.parseInt(scan.next()));
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        timeout1 = timers.remove(0);
        timeout2 = timers.remove(0);
        timeout3 = timers.remove(0);
        timeout4 = timers.remove(0);
        timeout5 = timers.remove(0);
        timeout6 = timers.remove(0);
        maxActivePlayerRefs = timers.remove(0);

        return true;
    }

}