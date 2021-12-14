package com.mandarin.window.gui.editor;

import com.mandarin.logic.entity.Actor;
import com.mandarin.logic.entity.border.Border;

import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Authored by Nykolas Farhangi
 * Created 9/18/16 at 7:26 PM
 */
public class EditorMouseInputListener extends MouseInputAdapter {

    public static final int BUILD = 0, SELECT = 1;
    private int mode;

    private ArrayList<Actor> selectedActors;
    private Point start, end;

    private EditorPanel panel;
    private JPopupMenu active;

    public EditorMouseInputListener(EditorPanel panel) {
        this.panel = panel;
        mode = BUILD;
        selectedActors = new ArrayList<>();
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.isPopupTrigger()) {
            active = panel.getPopupMenu();
            panel.getActors().stream().filter(actor -> actor.getDimensions().contains(e.getPoint())).forEach(actor -> active = actor.getPopupMenu());
            active.show(e.getComponent(), e.getX(), e.getY());
        } else {
            start = e.getPoint();
            end = start;
            if(!selectedActors.isEmpty()) {
                for(Actor actor : selectedActors) actor.setStatus(Actor.NORMAL);
                selectedActors.clear();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        end = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(start != end) {
            if(mode == BUILD) {
                Border border = new Border(getPreview(), 0, Border.TANGIBLE);
                border.initPopupMenu(panel);
                panel.getActors().add(border);
            } else if(mode == SELECT) {
                selectedActors.addAll(panel.getActors().stream().filter(actor -> actor.getDimensions().intersects(getPreview())).collect(Collectors.toList()));
                for(Actor actor : selectedActors) actor.setStatus(Actor.SELECTED);
            }
        }
        start = end = new Point(0, 0);
    }

    public Rectangle getPreview() {
        if(start == null) return null;
        int width = (int) Math.abs(start.getX() - end.getX()), height = (int) Math.abs(start.getY() - end.getY());
        return new Rectangle((int) (end.getX() > start.getX() ? start.getX() : end.getX()), (int) (end.getY() > start.getY() ? start.getY() : end.getY()), width, height);
    }
}