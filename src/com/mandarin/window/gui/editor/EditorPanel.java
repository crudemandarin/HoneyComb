package com.mandarin.window.gui.editor;

import com.mandarin.logic.Menuable;
import com.mandarin.logic.entity.Actor;
import com.mandarin.window.gui.Frame;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Authored by Nykolas Farhangi
 * Created 9/1/16 at 11:49 AM
 */

public class EditorPanel extends com.mandarin.window.gui.Panel implements Menuable {

    private ArrayList<Actor> actors;
    private EditorMouseInputListener listener;
    private boolean grid;

    private JPopupMenu popupMenu;
    private JMenu menuAdd, menuAddCharacter, menuAddScenery, menuChangeBackground;

    public EditorPanel(Frame frame) {
        super(frame);

        actors = new ArrayList<>();
        listener = new EditorMouseInputListener(this);
        grid = true;

        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fill(getBounds());

        if (grid) {
            g.setColor(Color.LIGHT_GRAY);
            for(int x = 1; x < getWidth()/25; x++)
                for(int y = 1; y < getHeight()/25; y++) {
                    g.drawLine(25*x, 0, 25*x, getHeight());
                    g.drawLine(0, 25*y, getWidth(), 25*y);
                }
        }

        for(Actor actor : actors) actor.paint(g2);

        g2.setColor(Color.DARK_GRAY);
        if(listener.getPreview() != null) g2.draw(listener.getPreview());

        g2.dispose();
    }

    public ArrayList<Actor> getActors() {
        return actors;
    }

    public EditorMouseInputListener getListener() {
        return listener;
    }

    public void toggleGrid() {
        grid = !grid;
    }

    @Override
    public void update() {

    }

    @Override
    public void init() {
        initPopupMenu(this);
    }

    @Override
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    @Override
    public void initPopupMenu(EditorPanel panel) {
        popupMenu = new JPopupMenu("Panel");

        menuAdd = new JMenu("Add..");
        menuAddCharacter = new JMenu("Character..");
        menuAddScenery = new JMenu("Scenery..");
        menuChangeBackground = new JMenu("Change Background..");

        menuAdd.add(menuAddCharacter);
        menuAdd.add(menuAddScenery);
        popupMenu.add(menuAdd);
        popupMenu.add(menuChangeBackground);
    }
}