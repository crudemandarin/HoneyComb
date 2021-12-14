package com.mandarin.window;

import com.mandarin.window.gui.Frame;
import com.mandarin.window.gui.editor.EditorFrame;
import com.mandarin.window.gui.game.GameFrame;
import com.mandarin.window.gui.menu.MenuFrame;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Authored by Nykolas Farhangi
 * Created 9/15/16 at 9:50 AM
 */

public class FrameManager {

    public static final String RH_VERSION = "v4.3A", SE_VERSION = "v1.2A";

    private ArrayList<Frame> frames;
    private int frame_index;

    public FrameManager() {
        frames = new ArrayList<>();
        init();

        System.out.print("Menu (0), Game (1), Scene Editor (2)? ");
        frame_index = new Scanner(System.in).nextInt();
        frames.get(frame_index).init();
    }

    private void init() {
        frames.add(new MenuFrame(this, "Random Heroes " + RH_VERSION + " (Menu) @crudemandarin", new Dimension(550, 650)));
        frames.add(new GameFrame(this, "Random Heroes " + RH_VERSION + " (Game) @crudemandarin", new Dimension(1300, 600)));
        frames.add(new EditorFrame(this, "Random Heroes " + RH_VERSION + " Scene Editor " + SE_VERSION + " @crudemandarin", new Dimension(950, 600)));
    }

    protected void open(int frame_index) {
        this.frame_index = frame_index;

    }

    protected void close(int frame_index) {

    }
}