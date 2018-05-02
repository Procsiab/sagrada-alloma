package server;

import server.threads.GameManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class SReferences {
    public static Integer activePlayers;
    public static ArrayList<String> uuidRef = new ArrayList<>();
    public static ArrayList<String> left = new ArrayList<>();
    public static ArrayList<String> ipRef = new ArrayList<>();
    public static ArrayList<Integer> portRef = new ArrayList<>();
    public static ArrayList<Boolean> isSocketRef = new ArrayList<>();
    public static ArrayList<Integer> gameIdRef = new ArrayList<>();
    public static ArrayList<Player> playerRef = new ArrayList<>();
    public static ArrayList <GameManager> gameRef = new ArrayList<>();
}
