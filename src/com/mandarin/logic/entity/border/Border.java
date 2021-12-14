package com.mandarin.logic.entity.border;

import com.mandarin.logic.entity.Actor;
import com.mandarin.window.gui.editor.EditorPanel;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

/**
 * Authored by Nykolas Farhangi
 * Created 3/1/16 at 7:51 AM
 */

public class Border extends Actor {

    public static final int CATCH_SPACE = 10;

    public static final int INTANGIBLE = 0, PARTIALLY_TANGIBLE = 1, TANGIBLE = 2;
    protected int tangibility;

    public Border(Rectangle dimensions, int layer, int tangibility) {
        super(dimensions, layer);
        this.tangibility = tangibility;
    }

    public int getTangibility() {
        return tangibility;
    }

    @Override
    public void act() {     }

    @Override
    public void paint(Graphics2D g) {
        super.paint(g);
        switch(tangibility) {
            case INTANGIBLE:
                g.setColor(Color.GRAY);
                g.fill(getDimensions());
                g.setColor(Color.WHITE);
                g.draw(getDimensions());
                break;
            case PARTIALLY_TANGIBLE:
                g.setColor(Color.DARK_GRAY);
                g.fill(getDimensions());
                g.setColor(Color.WHITE);
                g.draw(getDimensions());
                break;
            case TANGIBLE:
                g.setColor(Color.BLACK);
                g.fill(getDimensions());
                g.setColor(Color.WHITE);
                g.draw(getDimensions());
                break;
            default:
                g.setColor(Color.RED);
                g.draw(getDimensions());
                g.drawString("INVALID TANGIBILITY", (int) dimensions.getX() + 10, (int) dimensions.getY() + 10);
                break;
        }
    }

    @Override
    protected void initAnimations() {

    }

    //Scene Designer Code
    private JMenu menuChangeSkin, menuChangeTangibility;
    private ButtonGroup bgChangeTangibility;
    private JRadioButtonMenuItem rmenuItemTangible, rmenuItemPartiallyTangible, rmenuItemIntangible;

    protected JMenu menuChangeInstance;
    protected JRadioButtonMenuItem rmenuItemMovableBorder;

    @Override
    public void initPopupMenu(EditorPanel panel) {
        super.initPopupMenu(panel);

        //Change Skin
        menuChangeSkin = new JMenu("Change Skin..");

        //Change Instance
        menuChangeInstance = new JMenu("Change Instance..");
        rmenuItemMovableBorder = new JRadioButtonMenuItem("Movable Border");
        rmenuItemMovableBorder.addActionListener(this::changeInstanceToMovableBorder);

        //Change Tangibility
        menuChangeTangibility = new JMenu("Change Tangibility..");
        bgChangeTangibility = new ButtonGroup();
        rmenuItemTangible = new JRadioButtonMenuItem("Tangible");
        rmenuItemTangible.addActionListener(this::changeTangibilityActionListener);
        rmenuItemTangible.setSelected(true);
        rmenuItemPartiallyTangible = new JRadioButtonMenuItem("Partially Tangible");
        rmenuItemPartiallyTangible.addActionListener(this::changeTangibilityActionListener);
        rmenuItemIntangible = new JRadioButtonMenuItem("Intangible");
        rmenuItemIntangible.addActionListener(this::changeTangibilityActionListener);

        menuChangeInstance.add(rmenuItemMovableBorder);
        bgChangeTangibility.add(rmenuItemIntangible);
        bgChangeTangibility.add(rmenuItemPartiallyTangible);
        bgChangeTangibility.add(rmenuItemTangible);
        menuChangeTangibility.add(rmenuItemIntangible);
        menuChangeTangibility.add(rmenuItemPartiallyTangible);
        menuChangeTangibility.add(rmenuItemTangible);

        getPopupMenu().add(menuChangeTangibility, 0);
        getPopupMenu().add(menuChangeInstance, 0);
        getPopupMenu().add(menuChangeSkin, 0);
    }

    public static Border getBorderInstance(MovableBorder border) {
        return new Border(border.dimensions, border.getLayer(), border.getTangibility());
    }

    private void changeInstanceToMovableBorder(ActionEvent evt) {
        Border border = MovableBorder.getMovableBorderInstance(this);
        border.initPopupMenu(getPanel());
        getPanel().getActors().add(border);
        getPanel().getActors().remove(this);
    }

    private void changeTangibilityActionListener(ActionEvent evt) {
        for (java.util.Enumeration<javax.swing.AbstractButton> buttons = bgChangeTangibility.getElements(); buttons.hasMoreElements(); ) {
            javax.swing.AbstractButton button = buttons.nextElement();

            if(button.isSelected()) {
                if(button == rmenuItemIntangible) tangibility = INTANGIBLE;
                else if(button == rmenuItemPartiallyTangible) tangibility = PARTIALLY_TANGIBLE;
                else if(button == rmenuItemTangible) tangibility = TANGIBLE;
            }
        }
    }
}