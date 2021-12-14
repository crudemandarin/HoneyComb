package com.mandarin.logic.entity.neeple.character;

import com.mandarin.logic.Animation;
import com.mandarin.logic.entity.Actor;
import com.mandarin.logic.entity.neeple.MCharacter;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Authored by Nykolas Farhangi
 * Created 9/25/16 at 3:19 PM
 */

public class Player extends MCharacter {

    private static final int IDLE = 0, WALKING = 1, JUMPING = 2, FALLING = 3;
    private ArrayList<Animation> animations;
    private int index;

    public Player(Rectangle dimensions, int layer, Point2D.Double speed) {
        super(dimensions, layer, speed, -1);
        initAnimations();
    }

    @Override
    protected void initAnimations() {
        try {
            BufferedImage sprites = ImageIO.read(getClass().getResourceAsStream("/media/images/playersprites.gif"));
            ArrayList<BufferedImage[]> skins = new ArrayList<>();
            ArrayList<long[]> delays = new ArrayList<>();

            int[] frames = new int[] {2, 8, 1, 2};

            for(int i = 0; i < frames.length; i++) {
                BufferedImage[] bi = new BufferedImage[frames[i]];
                long[] di = new long[frames[i]];
                for(int j = 0; j < frames[i]; j++) {
                    bi[j] = sprites.getSubimage(j * 30, i * 30, 30, 30);
                    di[j] = 400;
                }
                skins.add(bi);
                delays.add(di);
            }

            animations = new ArrayList<>();
            for(int x = 0; x < skins.size(); x++) animations.add(new Animation(skins.get(x), delays.get(x)));
        } catch(Exception e) { e.printStackTrace(); }

        animation = animations.get(index = IDLE);
    }

    @Override
    protected void setVerticalStatus(int vdirection) {
        super.setVerticalStatus(vdirection);

        if(direction.getY() != STILL)  {
            if(direction.getY() == UP) index = JUMPING;
            else index = FALLING;
        }

        if(animation != null) animation = animations.get(index);
    }

    @Override
    protected void setDirection(double x, double y) {
        super.setDirection(x, y);

        if(direction.getY() == STILL) {
            if(direction.getX() == STILL) index = IDLE;
            else index = WALKING;
        }

        if(animation != null) animation = animations.get(index);
    }

    @Override
    public void interact(ArrayList<Actor> actors) {
        super.interact(actors);

        //System.out.println(actors);
    }
}
