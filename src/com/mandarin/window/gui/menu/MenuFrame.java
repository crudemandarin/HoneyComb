package com.mandarin.window.gui.menu;

import com.mandarin.window.FrameManager;
import com.mandarin.window.gui.Frame;

import java.awt.Dimension;

/**
 * Authored by Nykolas Farhangi
 * Created 9/16/16 at 10:06 AM
 */

public class MenuFrame extends Frame {

    public MenuFrame(FrameManager manager, String title, Dimension dimensions) {
        super(manager, title, dimensions);
        add(panel = new MenuPanel(this));
    }

    @Override
    public void init() {    }
}