package client.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
// I TRIED SO HARD, AND GOT SO FAR, IN THE END I CAN'T DYNAMYCALLY ALLOCATE CUSTOM FXML ITEMS.

public class WinnerController implements Initializable {
    private ArrayList<String> nicks;
    private ArrayList<Integer> scores;
    private ArrayList<Boolean> winner;
    @FXML
    public VBox leaderBoard;


    public WinnerController(ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) {
        System.out.println("Costructor WINNER");
        this.nicks=nicks;
        this.scores=scores;
        this.winner=winner;

    }

    @FXML
    public void initialize(URL location, ResourceBundle resources)  {
        System.out.println("Inizializzazione WINNER");
        System.out.println("Contenuto nicks WINNER" + nicks);






    }

    public void setVariables(ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner){
        System.out.println("Settin variables");

    }



}
