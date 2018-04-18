package server;

import shared.SharedClientGame;

import java.util.ArrayList;

public class ScoreBoard {
    public ArrayList<SharedClientGame> scoreMarkers = new ArrayList<>();
    public ArrayList<Integer> positions = new ArrayList<>();
    public Integer nPlayers;

    public ScoreBoard(ArrayList<SharedClientGame> clients){
        int i = 0;
        this.nPlayers = clients.size();
        while(i<nPlayers) {
            scoreMarkers = (ArrayList<SharedClientGame>) clients.clone();
            positions.add(1);
        }
    }

    public void setnPlayers(Integer nPlayers) {
        this.nPlayers = nPlayers;
    }

    public void moveScoreMarker(Integer i, Integer pos){
        positions.set(i, pos);
    }
}
