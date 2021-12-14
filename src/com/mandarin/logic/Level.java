package com.mandarin.logic;

import com.mandarin.logic.entity.Actor;
import com.mandarin.logic.entity.border.Border;
import com.mandarin.logic.entity.border.MovableBorder;
import com.mandarin.logic.entity.neeple.Character;
import com.mandarin.logic.entity.neeple.character.Player;
import com.mandarin.logic.entity.neeple.character.robot.Robot;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Authored by Nykolas Farhangi
 * Created 10/24/16 at 9:41 AM
 */

public class Level {

    public final String path;
    public String version;
    public int id;

    public ArrayList<Actor> actors;
    public ArrayList<Border> borders;
    public ArrayList<Character> characters;
    public Character character;

    public Point minimum, maximum;

    public Level(String path) {
        this.path = path;
    }

    public synchronized void init() {
        actors = new ArrayList<>();

        System.out.println("Finding Level Information...");
        version = "v0.1A";
        id = 1;

        System.out.println("Loading Borders...");
        borders = new ArrayList<>();
        loadBorders();

        System.out.println("Loading Characters...");
        characters = new ArrayList<>();
        loadCharacters();

        actors.addAll(borders);
        actors.addAll(characters);

        System.out.println("Defining Bounds...");
        double minX = actors.get(0).getDimensions().getMinX(), maxX = actors.get(0).getDimensions().getMaxX(),
               minY = actors.get(0).getDimensions().getMinY(), maxY = actors.get(0).getDimensions().getMaxY();

        for(Actor actor : actors) {
            if(actor.getDimensions().getX() < minX) minX = actor.getDimensions().getX();
            if(actor.getDimensions().getMaxX() > maxX) maxX = actor.getDimensions().getMaxX();
            if(actor.getDimensions().getY() < minY) minY = actor.getDimensions().getY();
            if(actor.getDimensions().getMaxY() > maxY) maxY = actor.getDimensions().getMaxY();
        }

        minimum = new Point((int) minX, (int) minY);
        maximum = new Point((int) maxX, (int) maxY);

        System.out.println("Finding Main Character...");
        characters.stream().filter(Character::isMain).forEach(neeple -> character = neeple);

        if(character == null) {
            System.out.println("\tNo main character found - defining Character @0 as 'main'");
            character = characters.get(0);
        }
    }

    //TO BE REMOVED
    private synchronized void loadCharacters() {
        characters.add(new Player(new Rectangle(900, 700, 50, 50), 0, new Point2D.Double(3, 7)));

        characters.add(new Robot(new Point(300, 700),1));
        characters.add(new Robot(new Point(1000, 680),1));
        characters.add(new Robot(new Point(1100, 680),1));

        characters.get(0).setMain(true);
    }

    //TO BE REMOVED
    private synchronized void loadBorders() {
        //Top
        borders.add(new Border(new Rectangle(0, 0, 500, 50), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(500, 0, 500, 50), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(1000, 0, 500, 50), 0, Border.TANGIBLE));

        //Bottom
        borders.add(new Border(new Rectangle(0, 1000, 500, 50), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(500, 1000, 500, 50), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(1000, 1000, 500, 50), 0, Border.TANGIBLE));

        //Walls
        borders.add(new Border(new Rectangle(0, 50, 50, 950), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(1450, 50, 50, 950), 0, Border.TANGIBLE));

        //Floor 0
        borders.add(new Border(new Rectangle(50, 980, 100, 20), 0, Border.TANGIBLE));
        borders.add(new MovableBorder(new Rectangle(155, 800, 100, 20), 0, Border.PARTIALLY_TANGIBLE, new Rectangle(155, 800, 100, 200), new Point2D.Double(0, 1)));
        borders.add(new Border(new Rectangle(260, 980, 20, 20), 0, Border.TANGIBLE));

        borders.add(new Border(new Rectangle(480, 980, 300, 20), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(530, 960, 200, 20), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(580, 940, 100, 20), 0, Border.TANGIBLE));

        borders.add(new Border(new Rectangle(950, 980, 200, 20), 0, Border.TANGIBLE));

        //Floor 1
        borders.add(new Border(new Rectangle(50, 800, 100, 20), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(260, 800, 200, 20), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(330, 780, 130, 20), 0, Border.TANGIBLE));
        borders.add(new MovableBorder(new Rectangle(465, 780, 100, 20), 0, Border.TANGIBLE, new Rectangle(465, 780, 360, 20), new Point2D.Double(1, 0)));
        borders.add(new Border(new Rectangle(830, 780, 80, 20), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(910, 780, 70, 20), 0, Border.PARTIALLY_TANGIBLE));
        borders.add(new Border(new Rectangle(980, 780, 470, 20), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(1000, 800, 450, 20), 0, Border.TANGIBLE));

        //Top
        borders.add(new Border(new Rectangle(50, 150, 100, 20), 0, Border.PARTIALLY_TANGIBLE));

        borders.add(new Border(new Rectangle(50, 650, 20, 20), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(200, 650, 800, 20), 0, Border.PARTIALLY_TANGIBLE));

        borders.add(new Border(new Rectangle(50, 550, 20, 20), 0, Border.TANGIBLE));
        borders.add(new Border(new Rectangle(50, 450, 20, 20), 0, Border.TANGIBLE));
    }
}
