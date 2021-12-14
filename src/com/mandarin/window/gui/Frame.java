package com.mandarin.window.gui;

import com.mandarin.window.FrameManager;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;

/**
 * Authored by Nykolas Farhangi
 * Created 9/16/16 at 10:12 AM
 */

public abstract class Frame extends JFrame implements Runnable {

    protected FrameManager manager;
    protected Panel panel;

    public Frame(FrameManager manager, String title, Dimension dimensions) {
        super(title);

        this.manager = manager;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension((int) (dimensions.getWidth() + (getInsets().left + getInsets().right)), (int) (dimensions.getHeight() + (getInsets().top + getInsets().bottom))));
        setLayout(null);
        pack();
    }

    public Panel getPanel() {
        return panel;
    }

    public abstract void init();

    public void update() { panel.update(); }

    @Override
    public void run() {
        int waitToUpdate = 1000/60, waitToPaint = 1000/90;
        long lastUpdate = System.nanoTime(), lastPaint = System.nanoTime();

        while(true) {
            int updatesNeeded = ((int) (System.nanoTime() - lastUpdate)/1000000)/waitToUpdate;
            int framesNeeded = ((int) (System.nanoTime() - lastPaint)/1000000)/waitToPaint;

            for(int x = 0; x < updatesNeeded; x++) {
                update();
                lastUpdate = System.nanoTime();
            }

            if(framesNeeded >= 1) {
                panel.repaint();
                lastPaint = System.nanoTime();
            }

            try { Thread.sleep(5); }
            catch(Exception e) { System.out.println("Error sleeping in run method: " + e.getMessage()); }
        }
    }
}