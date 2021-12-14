package com.mandarin.window.gui;

import javax.swing.JPanel;
import java.awt.geom.Point2D;

/**
 * Authored by Nykolas Farhangi
 * Created 9/1/16 at 9:57 AM
 */

public abstract class Panel extends JPanel {

    private Frame frame;
    private Point2D.Double visual;

    public Panel(Frame frame) {
        this.frame = frame;
        setSize(frame.getSize());
        visual = new Point2D.Double();
    }

    public Point2D.Double getVisual() {
        return visual;
    }

    public Frame getFrame() {
        return frame;
    }

    public abstract void update();

    public abstract void init();
}