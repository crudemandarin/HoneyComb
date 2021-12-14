package com.mandarin.logic.entity;

import java.awt.geom.Point2D;

/**
 * Authored by Nykolas Farhangi
 * Created 3/2/16 at 12:39 PM
 */

public interface Movable {
    int RIGHT = 1, DOWN = 1, STILL = 0, LEFT = -1, UP = -1;

    void move();

    Point2D.Double getVelocity();
}
