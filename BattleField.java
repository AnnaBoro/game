package lesson4.game;

/**
 * Created by anna on 27.10.15.
 */
public class BattleField {

    private int BF_WIDTH = 576;
    private int BF_HEIGHT = 576;

    private String[][] battleField = {
            {"B", " ", "B", "B", "B", "B", "B", "B", "B"},
            {"B", " ", " ", " ", " ", " ", " ", " ", "B"},
            {"B", "B", " ", " ", "B", " ", " ", "B", "B"},
            {"B", " ", "B", " ", " ", " ", "B", " ", "B"},
            {"B", " ", "B", " ", " ", " ", " ", " ", "B"},
            {" ", " ", " ", "B", " ", "B", " ", "B", "B"},
            {" ", "B", " ", " ", " ", " ", " ", "B", "B"},
            {" ", " ", " ", "B", "B", "B", " ", " ", "B"},
            {"B", " ", " ", "B", " ", " ", " ", " ", "B"}};

    public BattleField() {

    }

    public String[][] getBattleField() {
        return battleField;
    }

    public int getBF_WIDTH() {
        return BF_WIDTH;
    }

    public int getBF_HEIGHT() {
        return BF_HEIGHT;
    }

    public String scanQuadrant(int v, int h) {

        return battleField[v][h];
    }

    public void updateQuadrant(int v, int h, String str) {

        battleField[v][h] = str;
    }

    public int getDimensionX() {

        return battleField[0][0].length();
    }

    public  int getDimensionY() {

        return battleField[0].length;
    }
}
