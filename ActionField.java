package lesson4.game;

import javax.swing.*;
import java.awt.*;

/**
 * Created by anna on 27.10.15.
 */
public class ActionField extends JPanel{

    private boolean COLORDED_MODE = false;

    private BattleField bf;
    private Tank1 tank;
    private Bullet bullet;

    public ActionField() throws Exception {


        bf = new BattleField();
        tank = new Tank1(this, bf);
        bullet = new Bullet(-100, -100, -1);
        initFrame();
    }

    public void runTheGame() throws Exception {

//        tank.clean();
//        tank.moveRandom();
        tank.moveToQuadrant(128, 64);
//        tank.fire();
//        tank.turn(4);
//        tank.fire();
//        tank.move();
//        tank.move();
//        tank.turn(1);
//        tank.turn(2);
//        tank.fire();
//        tank.move();
//        tank.fire();
    }

    private boolean processInterception() {

        if (isOnTheField()) {

            if (removeBrick(false)) {
                bullet.destroy();
            }
            return false;
        }
        return true;
    }

    public boolean isOnTheField() {

        if ((bullet.getX() > 0 &&  bullet.getX() < 575)
                && (bullet.getY() > 0 &&  bullet.getY() < 575)) {
            return true;
        }
        return false;
    }

    public boolean removeBrick(boolean removeType) {

        String quadrant;

        if (removeType) {
            quadrant = getQuadrant(tank.getX(), tank.getY());
        }
        else quadrant = getQuadrant(bullet.getX(), bullet.getY());

        int i = Integer.parseInt(quadrant.substring(0, quadrant.indexOf("_")));
        int j = Integer.parseInt(quadrant.substring(quadrant.indexOf("_") + 1, quadrant.length()));

        if (bf.scanQuadrant(i, j) == "B") {
            bf.updateQuadrant(i, j, " ");
            repaint();
            return true;
        }
        return false;
    }

    String getQuadrant(int v, int h) {

        int x = v / 64;
        int	y = h / 64;

        return y + "_" + x;
    }

    public String getQuadrantXY(int v, int h) {

        return (v - 1) * 64 + "_" + (h - 1) * 64;
    }

    public void initFrame() throws Exception {
        JFrame frame = new JFrame("BATTLE FIELD, DAY 2");
        frame.setLocation(750, 150);
        frame.setMinimumSize(new Dimension(bf.getBF_WIDTH(), bf.getBF_HEIGHT() + 22));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int i = 0;
        Color cc;
        for (int v = 0; v < 9; v++) {
            for (int h = 0; h < 9; h++) {
                if (COLORDED_MODE) {
                    if (i % 2 == 0) {
                        cc = new Color(252, 241, 177);
                    } else {
                        cc = new Color(233, 243, 255);
                    }
                } else {
                    cc = new Color(180, 180, 180);
                }
                i++;
                g.setColor(cc);
                g.fillRect(h * 64, v * 64, 64, 64);
            }
        }

        for (int j = 0; j < bf.getDimensionY(); j++) {
            for (int k = 0; k < bf.getDimensionY(); k++) {
                if (bf.scanQuadrant(j, k).equals("B")) {
                    String coordinates = getQuadrantXY(j + 1, k + 1);
                    int separator = coordinates.indexOf("_");
                    int y = Integer.parseInt(coordinates.substring(0, separator));
                    int x = Integer.parseInt(coordinates.substring(separator + 1));
                    g.setColor(new Color(0, 0, 255));
                    g.fillRect(x, y, 64, 64);
                }
            }
        }

        g.setColor(new Color(255, 0, 0));
        g.fillRect(tank.getX(), tank.getY(), 64, 64);

        g.setColor(new Color(0, 255, 0));
        if (tank.getDirection() == 1) {
            g.fillRect(tank.getX() + 20, tank.getY(), 24, 34);
        } else if (tank.getDirection() == 2) {
            g.fillRect(tank.getX() + 20, tank.getY() + 30, 24, 34);
        } else if (tank.getDirection() == 3) {
            g.fillRect(tank.getX(), tank.getY() + 20, 34, 24);
        } else {
            g.fillRect(tank.getX() + 30, tank.getY() + 20, 34, 24);
        }

        g.setColor(new Color(255, 255, 0));
        g.fillRect(bullet.getX(), bullet.getY(), 14, 14);
    }

    public void processMove(Tank1 tank) throws InterruptedException {

//        this.tank = tank;
        for (int i = 0; i < 64; i++) {

            if (tank.getDirection() == 1) {

                if (tank.getY() !=0) {
                    tank.updateY(-1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection() == 2) {

                if (tank.getY() != 512) {
                    tank.updateY(1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection() == 3) {

                if (tank.getX() != 0) {
                    tank.updateX(- 1);
                }
                else System.out.println("Wrong direction");
            }
            else if (tank.getDirection() == 4) {

                if (tank.getX() != 512) {
                    tank.updateX(1);
                }
                else System.out.println("Wrong direction");
            }
            repaint();
            Thread.sleep(tank.getSpeed()/2);

        }
        this.removeBrick(true);
    }

    public void processTurn(Tank1 tank) {

        repaint();
    }

    public void processFire(Bullet bullet) throws InterruptedException {

        this.bullet = bullet;
        while (isOnTheField()) {
            for (int i = 0; i < 64; ) {

                if (tank.getDirection() == 1) {
                    bullet.updateY(-1);
                    System.out.println(bullet.getY());
                }
                else if (tank.getDirection() == 2) {
                    bullet.updateY(1);
                    System.out.println(bullet.getY());
                }
                else if (tank.getDirection() == 3) {
                    bullet.updateX(-1);
                    System.out.println(bullet.getX());
                }
                else if (tank.getDirection() == 4) {
                    bullet.updateX(1);
                    System.out.println(bullet.getX());
                }
                processInterception();
                repaint();
                Thread.sleep(bullet.getSpeed());
                break;
            }
        }
    }
}
