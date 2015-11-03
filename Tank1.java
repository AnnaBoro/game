package lesson4.game;

import lesson4.game.ActionField;
import lesson4.game.BattleField;
import lesson4.game.Bullet;

/**
 * Created by anna on 27.10.15.
 */
public class Tank1 {

    private int direction = 1;

    private int x = 128;
    private int y = 512;

    private int speed = 10;

    private ActionField actionField;
    private BattleField battleField;

    public Tank1(ActionField actionField, BattleField battleField) {
        this(actionField, battleField, 128, 512, 1);
    }

    public Tank1(ActionField actionField, BattleField battleField, int x, int y, int direction) {

        this.actionField = actionField;
        this.battleField = battleField;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public void turn(int direction) {

        this.direction = direction;
        actionField.processTurn(this);
    }

    public void move() throws InterruptedException {

        actionField.processMove(this);
    }

    public void fire() throws InterruptedException {

        Bullet bullet = new Bullet(x + 25, y + 25, direction);
        actionField.processFire(bullet);
    }

    public void clean() throws InterruptedException {

        turn(3);
        while (isEmptyX()) {
            fire();
        }

        moveToQuadrant(getY(), 0);
        turn(1);
        while (isEmptyY()) {
            fire();
        }

        moveToQuadrant(0, 0);
        turn(4);
        while (isEmptyX()) {
            fire();
        }

        for (int i = 0; i < battleField.getBattleField().length; i++) {
            moveToQuadrant(0, i * 64);
            turn(2);
            while (isEmptyY()) {
                fire();
            }
        }
    }

    public boolean isEmptyY() {

        int index = getX() / 64;
        int firstPoint = 0;
        int endPoint = getY() / 64;

        if (getDirection() == 2) {
            firstPoint = getY() / 64;
            endPoint = battleField.getBattleField()[index].length;
        }

        for (int i = firstPoint; i < endPoint; i++) {

            if (battleField.getBattleField()[i][index] == "B") {
                return true;
            }
        }
        return false;
    }

    public boolean isEmptyX() {

        int index = getY() / 64;
        int firstPoint = 0;
        int endPoint = getX() / 64;

        if (getDirection() == 4) {
            firstPoint = getX() / 64;
            endPoint = battleField.getBattleField()[index].length;
        }

        for (int i = firstPoint; i < endPoint; i++) {

            if (battleField.getBattleField()[index][i] == "B") {
                return true;
            }
        }
        return false;
    }

    public int[] getRandomQuadrant() {

        int[] randomNumbers = getRandomNumbers();
        for (int i = 0; i < randomNumbers.length; i++) {

            if (randomNumbers[i] > 8) {
                randomNumbers[i] = randomNumbers[i] - 1;
            }
        }
        return randomNumbers;
    }

    public int[] getRandomNumbers() {

        String randNum = String.valueOf(System.currentTimeMillis());
        String randNum1 = randNum.substring(randNum.length()-1);
        String randNum2 = randNum.substring(randNum.length()-2, randNum.length()-1);
        int randNumInt1 = Integer.parseInt(randNum1);
        int randNumInt2 = Integer.parseInt(randNum2);
        int[] randomNumbers = {randNumInt1, randNumInt2};
        return randomNumbers;
    }

    public int getRandomDirection(){

        int direction = 0;
        int[] randomNumbers = getRandomNumbers();
        int randNumInt1 = randomNumbers[0];
        int randNumInt2 = randomNumbers[1];

        if (randNumInt1 > randNumInt2) {

            if (randNumInt1 % 2 == 0) {
                direction = 1;
            }
            else {
                direction = 2;
            }
        }
        else {

            if (randNumInt2 % 2 == 0) {
                direction = 3;
            }
            else {
                direction = 4;
            }
        }
        return direction;
    }

    public void moveRandom() throws InterruptedException {

        while (true) {
            turn(getRandomDirection());
            move();
        }
    }

    public void moveToQuadrant(int v, int h) throws InterruptedException {

        String quadrant = actionField.getQuadrant(v, h);
        int lineIndex = quadrant.indexOf("_");
        int tankXNew = 64 * Integer.parseInt(quadrant.substring(0, lineIndex));
        int tankYNew = 64 * Integer.parseInt(quadrant.substring(lineIndex+1));

        if ((tankXNew - getX()) > 0) {
            int steps = (tankXNew - getX()) / 64;
            for (int step = 0; step < steps; step++) {
                turn(4);
                if (battleField.getBattleField()[getY()/64][getX()/64 + 1].equals("B")) {
                    fire();
                }
                move();
            }
        } else if ((tankXNew - getX()) < 0) {
            int steps = Math.abs((tankXNew - getX()) / 64);
            for (int step = 0; step < steps; step++) {
                turn(3);
                if (battleField.getBattleField()[getY()/64][getX()/64 - 1].equals("B")) {
                    fire();
                }
                move();
            }
        }

        if ((tankYNew - getY()) > 0) {
            int steps = (tankYNew - getY()) / 64;
            for (int step = 0; step < steps; step++) {
                turn(2);
                if (battleField.getBattleField()[getY()/64 + 1][getX()/64].equals("B")) {
                    fire();
                }
                move();
            }
        } else if ((tankYNew - getY()) < 0) {
            int steps = Math.abs((getY() - tankYNew) / 64);
            for (int step = 0; step < steps; step++) {
                turn(1);
                if (battleField.getBattleField()[getY()/64 - 1][getX()/64].equals("B")) {
                    fire();
                }
                move();
            }
        }
    }

    public int getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void updateX(int i) {

        x += i;
    }

    public void updateY(int i) {

        y += i;
    }

    public int getSpeed() {
        return speed;
    }
}
