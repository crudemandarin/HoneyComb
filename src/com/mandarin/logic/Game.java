package com.mandarin.logic;

import com.mandarin.logic.entity.Actor;
import com.mandarin.logic.entity.neeple.Character;
import com.mandarin.window.gui.Panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Authored by Nykolas Farhangi
 * Created 3/1/16 at 7:48 AM
 */

public class Game implements KeyListener {

    private final Panel panel;
    private Level level;

    private ArrayList<Actor> actors, vfocusedActors, ufocusedActors;
    private Character character;

    private Point minimum, maximum;
    private Rectangle vfocus, ufocus;

    private boolean ready = false;

    public Game(Panel panel, Level level) {
        this.panel = panel;
        this.level = level;

        vfocus = new Rectangle(panel.getWidth() + 50, panel.getHeight() + 50);
        ufocus = new Rectangle(panel.getWidth() + 300, panel.getHeight() + 300);

        init();
    }

    private synchronized void init() {
        System.out.println("Initializing level...");
        level.init();
        actors = level.actors;
        character = level.character;

        minimum = level.minimum;
        maximum = level.maximum;

        System.out.println("Defining visual displacement...");
        double x = character.getDimensions().getX() - (Math.ceil(panel.getWidth()/2)), y = character.getDimensions().getY() - (Math.ceil(panel.getHeight()/2));
        if(x < minimum.getX()) x = minimum.getX();
        else if(x > maximum.getX() - panel.getWidth()) x = maximum.getX() - panel.getWidth();
        if(y < minimum.getY()) y = minimum.getY();
        else if(y > maximum.getY() - panel.getHeight()) y = maximum.getY() - panel.getHeight();
        panel.getVisual().setLocation(-x, -y);

        vfocusedActors = new ArrayList<>();
        ufocusedActors = new ArrayList<>();

        System.out.println("Loading Complete!");
        ready = true;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fill(panel.getBounds());

        updateVisual();
        updateFocus();

        g.translate(panel.getVisual().getX(), panel.getVisual().getY());
        vfocusedActors.forEach(actor -> actor.paint(g));
    }

    private void updateVisual() {
        double x = character.getDimensions().getX() - Math.ceil(panel.getWidth() >> 1),
               y = character.getDimensions().getY() - Math.ceil(panel.getHeight() >> 1);

        if(x < minimum.getX()) x = minimum.getX();
        else if(x > maximum.getX() - panel.getWidth()) x = maximum.getX() - panel.getWidth();

        if(y < minimum.getY()) y = minimum.getY();
        else if(y > maximum.getY() - panel.getHeight()) y = maximum.getY() - panel.getHeight();

        panel.getVisual().setLocation(panel.getVisual().getX() - (x + panel.getVisual().getX()) * 0.07, panel.getVisual().getY() - (y + panel.getVisual().getY()) * 0.07);
    }

    private void updateFocus() {
        vfocus.setLocation((int) -Math.ceil(panel.getVisual().getX()) - 25, (int) -Math.ceil(panel.getVisual().getY()) - 25);
        ufocus.setLocation((int) -Math.ceil(panel.getVisual().getX()) - 150, (int) -Math.ceil(panel.getVisual().getY()) - 150);

        for(Actor actor : actors)
            if (actor.getDimensions().intersects(ufocus)) {
                if (!ufocusedActors.contains(actor)) ufocusedActors.add(actor);

                if (actor.getDimensions().intersects(vfocus)) {
                    if (!vfocusedActors.contains(actor)) vfocusedActors.add(actor);
                } else vfocusedActors.remove(actor);
            } else ufocusedActors.remove(actor);
    }

    public void update() {
        ufocusedActors.forEach(Actor::act);
        check();
    }

    private void check() {
        ArrayList<Character> characters = (ArrayList<Character>) ufocusedActors.stream().filter(actor -> actor instanceof Character).map(actor -> (Character) actor).collect(Collectors.toList());

        for(Character character : characters) {
            ArrayList<Actor> actors = new ArrayList<>();
            for(Actor actor : ufocusedActors) if((character != actor) && (character.getDetection().intersects(actor.getDimensions()))) actors.add(actor);
            character.interact(actors);
        }
    }

    public boolean isReady() {
        return ready;
    }

    @Override
    public void keyTyped(KeyEvent e) {  }

    @Override
    public void keyPressed(KeyEvent e) {
        character.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        character.keyReleased(e);
    }
}