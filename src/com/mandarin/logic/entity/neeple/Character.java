package com.mandarin.logic.entity.neeple;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

/**
 * Authored by Nykolas Farhangi
 * Created 3/3/16 at 9:51 AM
 */

public abstract class Character extends Neeple {

    private Rectangle detection;
    private boolean main;

    protected Character(Rectangle dimensions, int layer, Point2D.Double speed) {
        super(dimensions, layer, speed);

        detection = new Rectangle((int) dimensions.getWidth()*2, (int) dimensions.getHeight()*2);
        main = false;
    }

    public Rectangle getDetection() {
        return detection;
    }

    public void setMain(boolean main) {
        this.main = main;
    }

    public boolean isMain() {
        return main;
    }

    @Override
    public void paint(Graphics2D g) {
        super.paint(g);

        g.setColor(Color.GREEN);
        if(main) g.drawString("M", (int) dimensions.getX(), (int) dimensions.getY() + 10);

        g.setColor(Color.ORANGE);
        g.draw(detection);

        g.setColor(Color.CYAN);
        g.draw(dimensions);
    }

    @Override
    public void move() {
        super.move();
        detection.setLocation((int) Math.ceil((dimensions.getX() + dimensions.getWidth()/2) - detection.getWidth()/2), (int) Math.ceil((dimensions.getY() + dimensions.getHeight()/2) - detection.getHeight()/2));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        double x = direction.getX(),
               y = direction.getY();
        int k = e.getKeyCode();

        if(k == KeyEvent.VK_RIGHT) x = RIGHT;
        else if(k == KeyEvent.VK_LEFT) x = LEFT;
        else if(k == KeyEvent.VK_UP) y = UP;
        else if(k == KeyEvent.VK_DOWN) y = DOWN;

        setDirection((int) x, (int) y);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        double x = direction.getX(),
               y = direction.getY();
        int k = e.getKeyCode();

        if(k == KeyEvent.VK_RIGHT || k == KeyEvent.VK_LEFT) x = STILL;
        if(k == KeyEvent.VK_UP || k == KeyEvent.VK_DOWN) y = STILL;

        setDirection((int) x, (int) y);
    }
}