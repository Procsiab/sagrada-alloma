package server;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
    private static Config config;
    public final Integer timeout1;
    public final Integer timeout2;
    public final Integer timeout3;
    public final Integer timeout4;
    public final Integer timeout5;
    public final Integer timeout6;
    public final Integer maxActivePlayerRefs;

    public Config(Boolean test) {

        this.timeout1 = 25000;
        this.timeout2 = 10;
        this.timeout3 = 10000;
        this.timeout4 = 5000;
        this.timeout5 = 5000;
        this.timeout6 = 200;
        this.maxActivePlayerRefs = 6;
    }

    public static void test(){
        config = new Config(true);
    }

    private Config(List<Integer> timers) {

        timeout1 = timers.remove(0);
        timeout2 = timers.remove(0);
        timeout3 = timers.remove(0);
        timeout4 = timers.remove(0);
        timeout5 = timers.remove(0);
        timeout6 = timers.remove(0);
        maxActivePlayerRefs = timers.remove(0);
    }

    public static Config getConfig() {
        return config;
    }

    public static Boolean read() {
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

        config = new Config(timers);
        return true;
    }
}