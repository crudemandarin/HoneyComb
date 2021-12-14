package com.mandarin.window.gui.game;

import com.mandarin.logic.Game;
import com.mandarin.logic.Level;
import com.mandarin.window.gui.Frame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Authored by Nykolas Farhangi
 * Created 3/2/16 at 11:51 PM
 */

public class GamePanel extends com.mandarin.window.gui.Panel {

    private BufferedImage buffer;
    private Game game;

    public GamePanel(Frame frame) {
        super(frame);
        buffer = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    }

    @Override
    public void init() {
        game = new Game(this, new Level("lol"));
        addKeyListener(game);
        setFocusable(true);
    }

    public void update() { if(game.isReady()) game.update(); }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D bg = (Graphics2D) buffer.getGraphics();
        if(game.isReady()) game.paint(bg);
        g.drawImage(buffer, 0, 0, null);
        bg.dispose();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }
}
