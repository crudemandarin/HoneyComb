package com.mandarin.logic.entity.neeple;

import com.mandarin.logic.entity.Actor;
import com.mandarin.logic.entity.border.Border;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Authored by Nykolas Farhangi
 * Created 3/3/16 at 9:51 AM
 */

public abstract class MCharacter extends Character {

    private static final double TERMINAL_VELOCITY = 7.5;

    private int jumpCap, jumps;
    private double jumpSpeed;

    public MCharacter(Rectangle dimensions, int layer, Point2D.Double speed, int jumps) {
        super(dimensions, layer, speed);

        this.jumps = jumpCap = jumps;
        jumpSpeed = speed.getY();
    }

    protected void setVerticalStatus(int vdirection) {
        switch (vdirection) {
            case UP:
                if (jumps > 0 || jumps == -1) {
                    if (jumps != -1) jumps--;
                    speed.setLocation(speed.getX(), jumpSpeed);
                    direction.setLocation(direction.getX(), UP);
                }
                break;
            case DOWN:
                if (direction.getY() != UP) jumps = (jumps > 0 ? jumps - 1 : 0);
                speed.setLocation(speed.getX(), 1.5);
                direction.setLocation(direction.getX(), DOWN);
                break;
            case STILL:
                jumps = jumpCap;
                speed.setLocation(speed.getX(), 0);
                direction.setLocation(direction.getX(), STILL);
                break;
        }
    }

    protected void setSpeed(Point2D.Double speed) {
        this.speed = new Point2D.Double(speed.getX(), this.speed.getY());
        jumpSpeed = speed.getY();
    }

    @Override
    protected void setDirection(double x, double y) {
        super.setDirection(x, direction.getY());
        if(direction.getY() != y) setVerticalStatus((int) y);
    }

    @Override
    public void setStanding(Border standing) {
        super.setStanding(standing);
        if(standing != null) { if(direction.getY() != UP) setDirection(direction.getX(), STILL); }
        else if(direction.getY() == STILL) setVerticalStatus(DOWN);
    }

    @Override
    public void move() {
        super.move();
        if(direction.getY() == UP) {
            if(speed.getY() > 1.5) speed.setLocation(speed.getX(), speed.getY() * 0.95);
            else setVerticalStatus(DOWN);
        } else if(direction.getY() == DOWN && speed.getY() < TERMINAL_VELOCITY)
            speed.setLocation(speed.getX(), speed.getY() * 1.05);
    }

    @Override
    public void interact(ArrayList<Actor> actors) {
        super.interact(actors);

        for (Actor actor : actors) {
            if(actor instanceof Border) {
                Border border = (Border) actor;
                switch (border.getTangibility()) {
                    case Border.PARTIALLY_TANGIBLE:
                        //If MCharacter is falling through a partially tangible border but is in a certain tolerance, will land on the border.
                        if(direction.getY() == DOWN && (border.getDimensions().getY() < dimensions.getMaxY() && dimensions.getMaxY() < border.getDimensions().getY() + Border.CATCH_SPACE)) {
                            dimensions.setLocation((int) dimensions.getX(), (int) (border.getDimensions().getY() - dimensions.getHeight()));
                            setStanding(border);
                        }
                        break;
                    case Border.TANGIBLE:
                        //If MCharacter was in a jump and his upper dimension interacts with a border above, set his direction downward
                        if(direction.getY() == UP && dimensions.getY() == border.getDimensions().getMaxY()) setVerticalStatus(DOWN);
                        break;
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        double x = direction.getX(),
               y = direction.getY();
        int k = e.getKeyCode();

        if(k == KeyEvent.VK_RIGHT) x = RIGHT;
        else if(k == KeyEvent.VK_LEFT) x = LEFT;
        else if(k == KeyEvent.VK_UP) y = UP;
        else if(k == KeyEvent.VK_DOWN) {
            if(standing != null && standing.getTangibility() == Border.PARTIALLY_TANGIBLE)
                dimensions.setLocation((int) dimensions.getX(), (int) dimensions.getY() + Border.CATCH_SPACE);
        }

        setDirection(x, y);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) setDirection(STILL, (int) direction.getY());
    }
}