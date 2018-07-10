package server.executables;

import server.threads.MainServer;
import shared.Dice;
import server.Overlay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PublicObject {

    private PublicObject() {
    }

    /**
     * player gains points for each row completed with different colors
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use1(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;
        int esito = 1;

        Dice dice;

        Set<Character> colors = new HashSet<>();

        while (i < 4) {
            colors.clear();
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice == null || !colors.add(dice.getColor()))
                    esito = 0;
                j++;
            }
            j = 0;
            i++;
            if (esito == 1)
                sum = sum + 6;
            esito = 1;
        }
        return sum;
    }

    /**
     * player gains points for each column completed with different colors
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use2(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;
        int esito = 1;

        Dice dice;

        Set<Character> colors = new HashSet<>();

        while (i < 5) {
            colors.clear();
            while (j < 4) {
                dice = overlay.getDicePositions()[j][i];
                if (dice == null || !colors.add(dice.getColor()))
                    esito = 0;
                j++;
            }
            j = 0;
            i++;
            if (esito == 1)
                sum = sum + 5;
            esito = 1;
        }
        return sum;
    }

    /**
     * player gains points for each column completed with different numbers
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use3(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;
        int esito = 1;

        Dice dice;

        Set<Integer> numbers = new HashSet<>();

        while (i < 5) {
            numbers.clear();
            while (j < 4) {
                dice = overlay.getDicePositions()[j][i];
                if (dice == null || !numbers.add(dice.getValue()))
                    esito = 0;
                j++;
            }
            j = 0;
            i++;
            if (esito == 1)
                sum = sum + 4;
            esito = 1;
        }
        return sum;
    }

    /**
     * player gains points for each row completed with different numbers
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use4(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;
        int esito = 1;

        Dice dice;

        Set<Integer> numbers = new HashSet<>();

        while (i < 4) {
            numbers.clear();
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice == null || !numbers.add(dice.getValue()))
                    esito = 0;
                j++;
            }
            j = 0;
            i++;
            if (esito == 1)
                sum = sum + 5;
            esito = 1;
        }
        return sum;
    }

    /**
     * player gains points for each set of one of each colors
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use5(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        Set<Character> colors1 = new HashSet<>();
        Set<Character> colors2 = new HashSet<>();
        Set<Character> colors3 = new HashSet<>();
        Set<Character> colors4 = new HashSet<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (!colors1.add(dice.getColor()))
                        if (!colors2.add(dice.getColor()))
                            if (!colors3.add(dice.getColor()))
                                colors4.add(dice.getColor());
                j++;
            }
            j = 0;
            i++;
        }

        if (colors1.size() == 5)
            sum = sum + 4;
        if (colors2.size() == 5)
            sum = sum + 4;
        if (colors3.size() == 5)
            sum = sum + 4;
        if (colors4.size() == 5)
            sum = sum + 4;

        return sum;
    }

    /**
     * player gains points for each set of one of each number
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use6(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        Set<Integer> shades1 = new HashSet<>();
        Set<Integer> shades2 = new HashSet<>();
        Set<Integer> shades3 = new HashSet<>();
        Set<Integer> shades4 = new HashSet<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (!shades1.add(dice.getValue()))
                        if (!shades2.add(dice.getValue()))
                            if (!shades3.add(dice.getValue()))
                                shades4.add(dice.getValue());
                j++;
            }
            j = 0;
            i++;
        }

        if (shades1.size() == 6)
            sum = sum + 5;
        if (shades2.size() == 6)
            sum = sum + 5;
        if (shades3.size() == 6)
            sum = sum + 5;
        if (shades4.size() == 6)
            sum = sum + 5;

        return sum;
    }

    /**
     * player gains points for each set of a pair of 5 and 6
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use7(Overlay overlay) {
        int i = 0;
        int j = 0;

        Dice dice;
        ArrayList<Boolean> shades5 = new ArrayList<>();
        ArrayList<Boolean> shades6 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.getValue().equals(5))
                        shades5.add(true);
                    else if (dice.getValue().equals(6))
                        shades6.add(true);
                j++;
            }
            j = 0;
            i++;
        }

        shades5.trimToSize();
        shades6.trimToSize();

        return Math.min(shades5.size(), shades6.size()) * 2;
    }

    /**
     * player gains points for each set of a pair of 3 and 4
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use8(Overlay overlay) {
        int i = 0;
        int j = 0;

        Dice dice;
        ArrayList<Boolean> shades3 = new ArrayList<>();
        ArrayList<Boolean> shades4 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.getValue().equals(3))
                        shades3.add(true);
                    else if (dice.getValue().equals(4))
                        shades4.add(true);
                j++;
            }
            j = 0;
            i++;
        }

        shades3.trimToSize();
        shades4.trimToSize();

        return Math.min(shades3.size(), shades4.size()) * 2;
    }

    /**
     * player gains points for each set of a pair of 1 and 2
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use9(Overlay overlay) {
        int i = 0;
        int j = 0;

        Dice dice;
        ArrayList<Boolean> shades1 = new ArrayList<>();
        ArrayList<Boolean> shades2 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.getValue().equals(1))
                        shades1.add(true);
                    else if (dice.getValue().equals(2))
                        shades2.add(true);
                j++;
            }
            j = 0;
            i++;
        }

        shades1.trimToSize();
        shades2.trimToSize();

        return Math.min(shades1.size(), shades2.size()) * 2;
    }

    /**
     * player gains points for every diagonally adjacent and same color dice
     * @param overlay is where the dices are placed
     * @return the score gained
     */
    public static Integer use10(Overlay overlay) {
        Dice[][] dices = MainServer.deepClone(overlay.getDicePositions());
        int i = 0;
        int j;
        int sum = 0;
        while (i < 4) {
            j = 0;
            while (j < 5) {
                sum = sum + computate(dices, 'a', i, j);
                j++;
            }
            i++;
        }
        return sum;
    }

    /**
     * @see #use10(Overlay)'s core
     * @param dices the matrix that resides in overlay
     * @param color the color of the dice
     * @param r row in the matrix
     * @param c column in the matrix
     * @return the score gained
     */
    private static Integer computate(Dice[][] dices, Character color,
                                     Integer r, Integer c) {

        if (r > 3 || r < 0 || c > 4 || c < 0)
            return 0;
        if (dices[r][c] == null)
            return 0;
        if (!dices[r][c].getColor().equals(color))
            return computate(dices, dices[r][c].getColor(), r, c);
        dices[r][c] = null;
        return 1 + computate(dices, color, r - 1, c - 1) +
                computate(dices, color, r - 1, c + 1) +
                computate(dices, color, r + 1, c + 1) +
                computate(dices, color, r + 1, c - 1);
    }

}