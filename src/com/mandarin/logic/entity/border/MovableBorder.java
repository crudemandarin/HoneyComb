package com.mandarin.logic.entity.border;

import com.mandarin.logic.entity.Movable;
import com.mandarin.window.gui.editor.EditorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;
import java.util.regex.Pattern;

/**
 * Authored by Nykolas Farhangi
 * Created 3/1/16 at 7:51 AM
 */

public class MovableBorder extends Border implements Movable {

    private Rectangle bounds;
    private Point2D.Double speed;
    private Point direction;

    public MovableBorder(Rectangle dimensions, int layer, int tangibility, Rectangle bounds, Point2D.Double speed) {
        super(dimensions, layer, tangibility);
        this.bounds = bounds;
        this.speed = speed;
        direction = new Point(RIGHT, DOWN);
    }

    @Override
    public void act() {
        move();
    }

    @Override
    public void paint(Graphics2D g) {
        super.paint(g);

        g.setColor(Color.RED);
        g.drawString("M", (int) dimensions.getX() + 4, (int)dimensions.getY() + 12);

        g.setColor(Color.LIGHT_GRAY);
        g.draw(bounds);
    }

    @Override
    public void move() {
        dimensions.setLocation((int) Math.ceil(dimensions.getX() + getVelocity().getX()), (int) Math.ceil(dimensions.getY() + getVelocity().getY()));

        //Checks if, in the next call of move(), the x and/or y extend their bounds. Inverses direction if true.
        if(dimensions.getMaxX() + getVelocity().getX() > bounds.getMaxX() || dimensions.getX() + getVelocity().getX() < bounds.getX())
            direction.setLocation(-direction.getX(), direction.getY());

        if(dimensions.getMaxY() + getVelocity().getY() > bounds.getMaxY() || dimensions.getY() + getVelocity().getY() < bounds.getY())
            direction.setLocation(direction.getX(), -direction.getY());
    }

    @Override
    public Point2D.Double getVelocity() { return new Point2D.Double((speed.getX() * direction.getX()), (speed.getY() * direction.getY())); }

    //Scene Designer Code
    private JRadioButtonMenuItem rmenuItemBorder;
    private JMenuItem menuItemSetMotion;
    private JTextField textFieldMinXPosition, textFieldMaxXPosition, textFieldMinYPosition, textFieldMaxYPosition,
            textFieldHorizontalVelocity, textFieldVerticalVelocity;
    private JComponent[] setMotionInputs;

    @Override
    public void initPopupMenu(EditorPanel panel) {
        super.initPopupMenu(panel);

        menuItemSetMotion = new JMenuItem("Set Motion");
        menuItemSetMotion.addActionListener(this::setMotionActionListener);
        textFieldMinXPosition = new JTextField(bounds.getX() + "");
        textFieldMaxXPosition = new JTextField(bounds.getMaxX() + "");
        textFieldMinYPosition = new JTextField(bounds.getY() + "");
        textFieldMaxYPosition = new JTextField(bounds.getMaxY() + "");
        textFieldHorizontalVelocity = new JTextField(speed.getX() + "");
        textFieldVerticalVelocity = new JTextField(speed.getY() + "");
        setMotionInputs = new JComponent[] {new JLabel("Left Bound:"), textFieldMinXPosition, new JLabel("Right Bound:"), textFieldMaxXPosition,
                                            new JLabel("Upper Bound:"), textFieldMinYPosition, new JLabel("Lower Bound:"), textFieldMaxYPosition,
                                            new JLabel("Horizontal Velocity:"), textFieldHorizontalVelocity, new JLabel("Vertical Velocity:"), textFieldVerticalVelocity};
        getPopupMenu().add(new JPopupMenu.Separator(), 0);
        getPopupMenu().add(menuItemSetMotion, 0);

        rmenuItemBorder = new JRadioButtonMenuItem("Border");
        rmenuItemBorder.addActionListener(this::changeInstanceToBorder);
        menuChangeInstance.remove(rmenuItemMovableBorder);
        menuChangeInstance.add(rmenuItemBorder);
    }

    protected static MovableBorder getMovableBorderInstance(Border border) {
        return new MovableBorder(border.getDimensions(), border.getLayer(), border.getTangibility(), border.getDimensions(), new Point2D.Double());
    }

    private void changeInstanceToBorder(ActionEvent evt) {
        Border border = Border.getBorderInstance(this);
        border.initPopupMenu(getPanel());
        getPanel().getActors().add(border);
        getPanel().getActors().remove(this);
    }

    private void setMotionActionListener(ActionEvent evt) {
        int r = JOptionPane.showConfirmDialog(getPanel(), setMotionInputs, "Set Motion", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(r == JOptionPane.OK_OPTION) {
            double minHorizontalBound = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldMinXPosition.getText()) ? textFieldMinXPosition.getText() : "" + dimensions.getX()),
                    maxHorizontalBound = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldMaxXPosition.getText()) ? textFieldMaxXPosition.getText() : "" + dimensions.getX()),
                    minVerticalBound = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldMinYPosition.getText()) ? textFieldMinYPosition.getText() : "" + dimensions.getX()),
                    maxVerticalBound = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldMaxYPosition.getText()) ? textFieldMaxYPosition.getText() : "" + dimensions.getX()),
                    horizontalVelocity = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldHorizontalVelocity.getText()) ? textFieldHorizontalVelocity.getText() : "" + dimensions.getX()),
                    verticalVelocity = Double.parseDouble(Pattern.matches("^(-?[1-9]+\\d*)$|^0$", textFieldVerticalVelocity.getText()) ? textFieldVerticalVelocity.getText() : "" + dimensions.getX());

            bounds.setBounds((int) minHorizontalBound, (int) minVerticalBound, (int) (maxHorizontalBound - minHorizontalBound), (int) (maxVerticalBound - minVerticalBound));
            speed.setLocation(horizontalVelocity, verticalVelocity);
        }

        setMotionBounds();
    }

    @Override
    protected int menuItemSetDimensionsActionPerformed(ActionEvent evt) {
        int x = super.menuItemSetDimensionsActionPerformed(evt);
        if(x == JOptionPane.CANCEL_OPTION) return x;

        int r = JOptionPane.showConfirmDialog(getPanel(), "Adjust bounds to dimensions?", "Set Dimensions", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(r == JOptionPane.YES_OPTION) {
            bounds.setBounds(dimensions);
            setMotionBounds();
        }
        return -1;
    }

    private void setMotionBounds() {
        textFieldMinXPosition.setText(bounds.getX() + "");
        textFieldMaxXPosition.setText(bounds.getMaxX() + "");
        textFieldMinYPosition.setText(bounds.getY() + "");
        textFieldMaxYPosition.setText(bounds.getMaxY() + "");
        textFieldHorizontalVelocity.setText(speed.getX() + "");
        textFieldVerticalVelocity.setText(speed.getY() + "");

    }
}