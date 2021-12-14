package com.mandarin.logic.entity.neeple.character.robot;

import com.mandarin.logic.entity.Actor;
import com.mandarin.logic.entity.border.Border;
import com.mandarin.logic.entity.neeple.Character;
import com.mandarin.logic.entity.neeple.MCharacter;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Authored by Nykolas Farhangi (@crudemandarin)
 * Created 4/23/2018 at 11:49 PM
 */
public class Robot extends MCharacter {

    private static final Dimension DIMENSION = new Dimension(30,50);
    private static final Point2D.Double WANDER_SPEED = new Point2D.Double(1, 5), ALERT_SPEED = new Point2D.Double(2, 5);
    private static final int JUMPS = 1, JUMP_HEIGHT = 50;

    private static final int WANDER = 0, CHASE = 1;
    private int mode;

    private Random rand = new Random();
    private long time;

    private Character target;

    public Robot(Point location, int layer) {
        super(new Rectangle(location, DIMENSION), layer, WANDER_SPEED, JUMPS);

        time = System.currentTimeMillis()/1000;
        mode = WANDER;
        target = null;
    }

    @Override
    public void act() {
        super.act();

        switch (mode) {
            case WANDER:
                long timeSinceLastUpdate = System.currentTimeMillis()/1000 - time;
                int action = rand.nextInt(1) + 2;

                if(timeSinceLastUpdate > action) {
                    int n = rand.nextInt(100);
                    if (n < 40) setDirection(STILL, direction.getY());
                    else if (n < 70) setDirection(RIGHT, direction.getY());
                    else setDirection(LEFT, direction.getY());
                    time = System.currentTimeMillis() / 1000;
                }
                break;
            case CHASE:
                if (target.getDimensions().getMaxX() + 50 < dimensions.getX()) setDirection(LEFT, direction.getY());
                else if (target.getDimensions().getX() - 50 > dimensions.getX()) setDirection(RIGHT, direction.getY());
                else setDirection(STILL, direction.getY());

                if (target.getDimensions().getMaxY() < dimensions.getY()) {
                    setDirection(direction.getX(), UP);
                }
                else if(standing != null && standing.getTangibility() == Border.PARTIALLY_TANGIBLE) setDirection(direction.getX(), DOWN);

                if (Math.abs(target.getDimensions().getX() - dimensions.getX()) > 300 || Math.abs(target.getDimensions().getY() - dimensions.getY()) > 300) {
                    mode = WANDER;
                    setSpeed(WANDER_SPEED);
                    target = null;
                }
                break;
        }
    }

    @Override
    protected void initAnimations() {
        animation = null;
    }

    @Override
    public void interact(ArrayList<Actor> actors) {
        super.interact(actors);

        for(Actor actor : actors) {
            if (actor instanceof Character) {
                Character character = (Character) actor;
                if (character.isMain()) {
                    mode = CHASE;
                    target = character;
                    setSpeed(ALERT_SPEED);
                }
            }

            if (actor instanceof Border) {
                if(getDimensions().intersects(actor.getDimensions())) {
                    System.out.println("Yooo");
                    if (dimensions.getMaxY() - JUMP_HEIGHT  < actor.getDimensions().getY()) setDirection(direction.getX(), UP);
                    else setDirection(-direction.getX(), direction.getY());
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Robot (" + Integer.toHexString(hashCode())
                + ") : STANDING (" + (standing != null ? Integer.toHexString(standing.hashCode()) : "null")
                + ") : S [" + speed.getX() + ", " + speed.getY() + "] : D [" + direction.getX() + ", " + direction.getY() + "]"
                + " M " + mode;
    }
}
