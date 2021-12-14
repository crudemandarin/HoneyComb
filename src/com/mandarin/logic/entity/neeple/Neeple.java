package com.mandarin.logic.entity.neeple;

import com.mandarin.logic.Interactable;
import com.mandarin.logic.entity.Actor;
import com.mandarin.logic.entity.Movable;
import com.mandarin.logic.entity.border.Border;
import com.mandarin.logic.entity.border.MovableBorder;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Authored by Nykolas Farhangi
 * Created 3/4/16 at 9:24 AM
 */

public abstract class Neeple extends Actor implements Movable, Interactable {

    protected Point2D.Double speed;
    protected Point direction;
    private int facing;

    protected Border standing;

    protected Neeple(Rectangle dimensions, int layer, Point2D.Double speed) {
        super(dimensions, layer);
        this.speed = speed;

        direction = new Point(STILL, DOWN);
        facing = RIGHT;
        standing = null;
    }

    protected void setDirection(double x, double y) {
        direction.setLocation(x, y);
        if(x != STILL) facing = (int) x;
    }

    public void setStanding(Border standing) {
        this.standing = standing;
    }

    public Border getStanding() {
        return standing;
    }

    public abstract void keyPressed(KeyEvent e);

    public abstract void keyReleased(KeyEvent e);

    @Override
    public void act() {
        if(animation != null) animation.update();
        move();
    }

    @Override
    public void paint(Graphics2D g) {
        super.paint(g);
        if(animation != null) g.drawImage(animation.getImage(), (int) (facing == LEFT ? dimensions.getMaxX() : dimensions.getX()), (int) dimensions.getY(), (int) dimensions.getWidth() * facing, (int) dimensions.getHeight(), null);
        else {
            g.setColor(Color.BLACK);
            g.drawRoundRect((int) dimensions.getX(), (int) dimensions.getY(), (int) dimensions.getWidth(), (int) dimensions.getHeight(), 10, 10);

            g.setColor(Color.BLUE);
            if(facing == LEFT) g.drawOval((int) (dimensions.getX() + dimensions.getWidth() * 0.10), (int) (dimensions.getY() + dimensions.getHeight() * 0.20), (int) (dimensions.getWidth() * 0.1), (int) (dimensions.getHeight() * 0.1));
            else if(facing == RIGHT) g.drawOval((int) (dimensions.getMaxX() - 2 * dimensions.getWidth() * 0.10), (int) (dimensions.getY() + dimensions.getHeight() * 0.20), (int) (dimensions.getWidth() * 0.1), (int) (dimensions.getHeight() * 0.1));
        }
    }

    @Override
    public void move() {
        dimensions.setLocation((int) Math.ceil(dimensions.getX() + getVelocity().getX() + (standing instanceof MovableBorder ? ((MovableBorder) standing).getVelocity().getX() : 0)),
                               (int) Math.ceil(dimensions.getY() + getVelocity().getY() + (standing instanceof MovableBorder ? ((MovableBorder) standing).getVelocity().getY() : 0)));
    }

    @Override
    public Point2D.Double getVelocity() {
        return new Point2D.Double(direction.getX() * speed.getX(), direction.getY() * speed.getY());
    }

    @Override
    public void interact(ArrayList<Actor> actors) {
        Border standing = null;

        for (Actor actor : actors) {
            if(actor instanceof Border) {
                Border border = (Border) actor;

                //Handles Border intersections
                if(dimensions.intersects(border.getDimensions())) {
                    if(border.getTangibility() == Border.TANGIBLE) {
                        double x = dimensions.getX(), y = dimensions.getY();
                        if (dimensions.intersection(border.getDimensions()).getHeight() >= dimensions.intersection(border.getDimensions()).getWidth()) {
                            if(dimensions.getX() < border.getDimensions().getX()) x = border.getDimensions().getX() - dimensions.getWidth();
                            else x = border.getDimensions().getMaxX();
                        } else {
                            if(dimensions.getY() < border.getDimensions().getY()) y = border.getDimensions().getY() - dimensions.getHeight();
                            else y = border.getDimensions().getMaxY();
                        }
                        dimensions.setLocation((int) x, (int) y);
                    }
                }

                //Handles Neeple's standing
                if((dimensions.getMaxY() == border.getDimensions().getY()) //The bottom of Neeple's dimensions is equal to the top of the Border's dimension
                        && (border.getDimensions().getX() - dimensions.getWidth() < dimensions.getX() //Neeple is within the Border's dimensions +/- Neeple's width
                        && dimensions.getMaxX() < border.getDimensions().getMaxX() + dimensions.getWidth())) {
                    standing = border;
                }
            }
        }

        if(standing != this.standing) setStanding(standing);
    }
}