package server;

import server.threads.GameManager;
import shared.NetworkRmiClientI;

import java.util.ArrayList;

public class SReferences {
    //MatchManager is static
    public static Integer activePlayers;
    //given the id of a player
    public static ArrayList<Integer> gameIdRef = new ArrayList<>();
    public static ArrayList<Player> playerRef = new ArrayList<>();
    public static ArrayList <GameManager> gameRef = new ArrayList<>();
    public static ArrayList<NetworkRmiClientI> rmiClientRef = new ArrayList<>();
    //further Arraylist if needed by socket
}
