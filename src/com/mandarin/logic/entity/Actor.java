package com.mandarin.logic.entity;

import com.mandarin.logic.Animation;
import com.mandarin.logic.Menuable;
import com.mandarin.window.gui.editor.EditorPanel;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

/**
 * Authored by Nykolas Farhangi
 * Created 3/1/16 at 7:49 AM
 */

public abstract class Actor implements Menuable {

    protected Animation animation;
    protected Rectangle dimensions;
    private int layer;

    protected Actor(Rectangle dimensions, int layer) {
        this.dimensions = dimensions;
        this.layer = layer;
    }

    public Rectangle getDimensions() {
        return dimensions;
    }

    public int getLayer() {
        return layer;
    }

    public abstract void act();

    public void paint(Graphics2D g) {
        if(status != NORMAL) {
            if(status == SELECTED) g.setColor(Color.YELLOW);
            else if(status == ISSUE) g.setColor(Color.RED);
            g.drawRect((int) dimensions.getX() - 1, (int) dimensions.getY() - 1, (int) dimensions.getWidth() + 2, (int) dimensions.getHeight() + 2);
        }
    }

    protected abstract void initAnimations();

    //Scene Designer Code
    private EditorPanel panel;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemSetDimensions, menuItemSetLayer, menuItemDelete;
    private JTextField textFieldXPosition, textFieldYPosition, textFieldWidth, textFieldHeight, textFieldLayer;
    private JComponent[] setDimensionInputs, setLayerInputs;

    public static int NORMAL = 0, SELECTED = 1, ISSUE = 2;
    private int status;

    public EditorPanel getPanel() {
        return panel;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    @Override
    public void initPopupMenu(EditorPanel panel) {
        this.panel = panel;
        popupMenu = new JPopupMenu();

        //Set Dimensions
        menuItemSetDimensions = new JMenuItem("Set Dimensions");
        menuItemSetDimensions.addActionListener(this::menuItemSetDimensionsActionPerformed);
        textFieldXPosition = new JTextField((int) dimensions.getX() + "");
        textFieldYPosition = new JTextField((int) dimensions.getY() + "");
        textFieldWidth = new JTextField((int) dimensions.getWidth() + "");
        textFieldHeight = new JTextField((int) dimensions.getHeight() + "");
        setDimensionInputs = new JComponent[] {
                new JLabel("X Position:"), textFieldXPosition, new JLabel("Y Position:"), textFieldYPosition,
                new JLabel("Width:"), textFieldWidth, new JLabel("Height:"), textFieldHeight };

        //Set Layer
        menuItemSetLayer = new JMenuItem("Set Layer");
        menuItemSetLayer.addActionListener(this::menuItemSetLayerActionPerformed);
        textFieldLayer = new JTextField(layer + "");
        setLayerInputs = new JComponent[] { new JLabel("Layer:"), textFieldLayer};

        //Delete
        menuItemDelete = new JMenuItem("Delete");
        menuItemDelete.addActionListener(this::menuItemDeleteActionPerformed);

        popupMenu.addSeparator();
        popupMenu.add(menuItemSetDimensions);
        popupMenu.add(menuItemSetLayer);
        popupMenu.addSeparator();
        popupMenu.add(menuItemDelete);
    }

    protected int menuItemSetDimensionsActionPerformed(ActionEvent evt) {
        int r = JOptionPane.showConfirmDialog(panel, setDimensionInputs, "Set Dimension", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(r == JOptionPane.OK_OPTION) {
            double x = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldXPosition.getText()) ? textFieldXPosition.getText() : "" + dimensions.getX()),
                   y = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldYPosition.getText()) ? textFieldYPosition.getText() : "" + dimensions.getY()),
                   width = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldWidth.getText()) ? textFieldWidth.getText() : "" + dimensions.getWidth()),
                   height = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldHeight.getText()) ? textFieldHeight.getText() : "" + dimensions.getHeight());

            dimensions.setBounds((int) x, (int) y, (int) width, (int) height);
        }

        textFieldXPosition.setText((int) dimensions.getX() + "");
        textFieldYPosition.setText((int) dimensions.getY() + "");
        textFieldWidth.setText((int) dimensions.getWidth() + "");
        textFieldHeight.setText((int) dimensions.getHeight() + "");

        return r;
    }

    private void menuItemSetLayerActionPerformed(ActionEvent evt) {
        int r = JOptionPane.showConfirmDialog(panel, setLayerInputs, "Set Layer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(r == JOptionPane.OK_OPTION)
            if (Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldLayer.getText())) layer = Integer.parseInt(textFieldLayer.getText());
        textFieldLayer.setText(layer + "");
    }

    private void menuItemDeleteActionPerformed(ActionEvent evt) {
        panel.getActors().remove(this);
    }
}