package com.mandarin.logic;

import com.mandarin.logic.entity.Actor;

import java.util.ArrayList;

/**
 * Authored by Nykolas Farhangi
 * Created 3/22/16 at 8:01 PM
 */

public interface Interactable {
    void interact(ArrayList<Actor> actors);
}
