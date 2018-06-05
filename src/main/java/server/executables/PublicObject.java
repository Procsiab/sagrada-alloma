package server.executables;

import shared.Dice;
import shared.Overlay;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PublicObject {

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
                if (dice == null)
                    esito = 0;
                else if (!colors.add(dice.color))
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
                if (dice == null)
                    esito = 0;
                else if (!colors.add(dice.color))
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
                if (dice == null)
                    esito = 0;
                else if (!numbers.add(dice.value))
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
                if (dice == null)
                    esito = 0;
                else if (!numbers.add(dice.value))
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

    public static Integer use5(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        Set<Character> colors1 = new HashSet<>();
        Set<Character> colors2 = new HashSet<>();
        Set<Character> colors3 = new HashSet<>();
        Set<Character> colors4 = new HashSet<>();
        Set<Character> colors5 = new HashSet<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (!colors1.add(dice.color))
                        if (!colors2.add(dice.color))
                            if (!colors3.add(dice.color))
                                if (!colors4.add(dice.color))
                                    colors5.add(dice.color);
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
        if (colors5.size() == 5)
            sum = sum + 4;

        return sum;
    }

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
                    if (!shades1.add(dice.value))
                        if (!shades2.add(dice.value))
                            if (!shades3.add(dice.value))
                                shades4.add(dice.value);
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

    public static Integer use7(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        ArrayList<Boolean> shades5 = new ArrayList<>();
        ArrayList<Boolean> shades6 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.value == 5)
                        shades5.add(true);
                    else if (dice.value == 6)
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

    public static Integer use8(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        ArrayList<Boolean> shades3 = new ArrayList<>();
        ArrayList<Boolean> shades4 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.value == 3)
                        shades3.add(true);
                    else if (dice.value == 4)
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

    public static Integer use9(Overlay overlay) {
        int i = 0;
        int j = 0;
        int sum = 0;

        Dice dice;
        ArrayList<Boolean> shades1 = new ArrayList<>();
        ArrayList<Boolean> shades2 = new ArrayList<>();

        while (i < 4) {
            while (j < 5) {
                dice = overlay.getDicePositions()[i][j];
                if (dice != null)
                    if (dice.value == 1)
                        shades1.add(true);
                    else if (dice.value == 2)
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

    public static Integer use10(Overlay overlay1) {
        Overlay overlay = overlay1.deepClone();
        Dice[][] dices = overlay.getDicePositions();
        int i = 1;
        int j = 1;
        int sum = 0;
        while (i < 5) {
            j = 1;
            while (j < 6) {
                sum = sum + computate(dices, 'a', i, j, true);
                j++;
            }
            i++;
        }
        return sum;
    }

    private static Integer computate(Dice[][] dices, Character color,
                                     Integer r, Integer c, Boolean firstTime) {

        if (r > 4 || r < 1 || c > 5 || c < 1)
            return 0;
        if (dices[r - 1][c - 1] == null)
            return 0;
        if (dices[r - 1][c - 1].getColor() != color)
            return computate(dices, dices[r - 1][c - 1].getColor(), r, c, true);
        dices[r - 1][c - 1] = null;
        Integer ul = computate(dices, color, r - 1, c - 1, false);
        Integer ur = computate(dices, color, r - 1, c + 1, false);
        Integer dr = computate(dices, color, r + 1, c + 1, false);
        Integer dl = computate(dices, color, r + 1, c - 1, false);
        if (firstTime && ul + ur + dr + dl == 0)
            return 0;
        return 1 + ul + ur + dr + dl;
    }

}