package com.mandarin.window.gui.game;

import com.mandarin.window.FrameManager;
import com.mandarin.window.gui.Frame;

import java.awt.Dimension;

/**
 * Authored by Nykolas Farhangi
 * Created 9/16/16 at 9:57 AM
 */

public class GameFrame extends Frame {

    public GameFrame(FrameManager manager, String title, Dimension dimensions) {
        super(manager, title, dimensions);
        add(panel = new GamePanel(this));

        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void init() {
        panel.init();
        new Thread(this).start();
        setVisible(true);
    }
}
