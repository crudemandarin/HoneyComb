package com.mandarin.logic;

import com.mandarin.window.gui.editor.EditorPanel;

import javax.swing.*;

/**
 * Authored by Nykolas Farhangi
 * Created 9/13/16 at 7:58 PM
 */

public interface Menuable {
    JPopupMenu getPopupMenu();

    void initPopupMenu(EditorPanel panel);
}
