package lesson4.game;

/**
 * Created by anna on 27.10.15.
 */
public class Bullet {

    private int x = -100;
    private int y = -100;

    private int speed = 5;
    private int direction;

    public Bullet(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public void updateX(int i) {

        x += i;
    }

    public void updateY(int i) {

        y += i;
    }

    public void destroy() {

        x = -100;
        y = -100;
    }
}
