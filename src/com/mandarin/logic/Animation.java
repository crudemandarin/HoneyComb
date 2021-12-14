package com.mandarin.logic;

import java.awt.image.BufferedImage;

/**
 * Authored by Nykolas Farhangi
 * Created 9/25/16 at 2:56 PM
 */

public class Animation {

    private BufferedImage[] skins;
    private long[] delays;
    private int index, count;

    private long start;

    public Animation(BufferedImage[] skins, long[] delays) {
        this.skins = skins;
        this.delays = delays;

        index = count = 0;
        start = System.nanoTime();

        if(skins.length != delays.length) System.out.println("ERROR: skins.length != delays.length!");
    }

    public void update() {
        if(delays[index] == -1) return;

        if(delays[index] < (System.nanoTime() - start)/1000000) {
            index++;
            start = System.nanoTime();

            if(index == skins.length) {
                index = 0;
                count++;
            }
        }
    }

    public int getCount() {
        return count;
    }

    public BufferedImage getImage() {
        return skins[index];
    }
}
