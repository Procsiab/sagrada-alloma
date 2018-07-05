package client.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

// I TRIED SO HARD, AND GOT SO FAR, IN THE END I CAN'T DYNAMYCALLY ALLOCATE CUSTOM FXML ITEMS.

/**
 * Controller associated with {@code Winner.fxml}. Shows the leaderboard with nicknames and scores.
 */
public class WinnerController implements Initializable {
    private ArrayList<String> nicks;
    private ArrayList<Integer> scores;
    private ArrayList<Boolean> winner;
    private ArrayList<Text> nicknames = new ArrayList<>();
    private ArrayList<Text> scoresText = new ArrayList<>();
    private ArrayList<AnchorPane> cards = new ArrayList<>();

    @FXML
    private Text player1,player2,player3,player4;
    @FXML
    private Text score1,score2,score3,score4;
    @FXML
    private AnchorPane card1,card2,card3,card4;


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
        initNicknames();
        initCards();
        initScores();
        setData(nicks,scores,winner);




    }

    private void initScores() {
        scoresText.add(score1);
        scoresText.add(score2);
        scoresText.add(score3);
        scoresText.add(score4);


    }

    private void initCards() {
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);
        for(int i=0;i<cards.size();i++){
            cards.get(i).setVisible(false);
        }

    }

    private void setData(ArrayList<String> nicks, ArrayList<Integer> scores, ArrayList<Boolean> winner) {
        for(int i=0;i<nicks.size();i++){
            nicknames.get(i).setText(nicks.get(i));
            if(scores!=null) {
                scoresText.get(i).setText(scores.get(i).toString());
            }
            cards.get(i).setVisible(true);
        }

    }

    private void initNicknames() {
        nicknames.add(player1);
        nicknames.add(player2);
        nicknames.add(player3);
        nicknames.add(player4);


    }

}
