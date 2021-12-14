package com.mandarin.window.gui.editor;

import com.mandarin.window.FrameManager;
import com.mandarin.window.gui.Frame;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Authored by Nykolas Farhangi
 * Created 9/16/16 at 10:07 AM
 */

public class EditorFrame extends Frame {
    private JMenuBar menu;
    private JMenu menuFile, menuEdit, menuView, menuViewShift, menuTools;
    protected JMenuItem menuItemNew, menuItemOpen, menuItemCloseProject, menuItemSettings, menuItemSave, menuItemSaveAs,
                      menuItemUndo, menuItemRedo, menuItemDeleteSelected,
                      menuItemZoomIn, menuItemZoomOut, menuItemToggleGrid, menuItemShiftLeft, menuItemShiftUp, menuItemShiftDown, menuItemShiftRight,
                      menuItemTestProject;
    private ButtonGroup bgToolMode;
    private JRadioButtonMenuItem rmenuItemBuildMode, rmenuItemSelectMode;


    public EditorFrame(FrameManager manager, String title, Dimension dimensions) {
        super(manager, title, dimensions);
        add(panel = new EditorPanel(this));

        setResizable(true);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.setSize(e.getComponent().getSize());
                update();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void init() {
        panel.init();

        menu = new JMenuBar();

        menuFile = new JMenu("File");
        menuItemNew = new JMenuItem("New");
        menuItemNew.addActionListener(this::menuItemNewActionPerformed);
        menuFile.add(menuItemNew);
        menuItemOpen = new JMenuItem("Open");
        menuItemOpen.addActionListener(this::menuItemOpenActionPerformed);
        menuFile.add(menuItemOpen);
        menuItemCloseProject = new JMenuItem("Close Project");
        menuItemCloseProject.addActionListener(this::menuItemCloseProjectActionPerformed);
        menuFile.add(menuItemCloseProject);
        menuFile.addSeparator();
        menuItemSettings = new JMenuItem("Settings");
        menuItemSettings.addActionListener(this::menuItemSettingsActionPerformed);
        menuFile.add(menuItemSettings);
        menuFile.addSeparator();
        menuItemSave = new JMenuItem("Save");
        menuItemSave.addActionListener(this::menuItemSaveActionPerformed);
        menuFile.add(menuItemSave);
        menuItemSaveAs = new JMenuItem("Save As");
        menuItemSaveAs.addActionListener(this::menuItemSaveAsActionPerformed);
        menuFile.add(menuItemSaveAs);
        menu.add(menuFile);

        menuEdit = new JMenu("Edit");
        menuItemUndo = new JMenuItem("Undo");
        menuItemUndo.addActionListener(this::menuItemUndoActionPerformed);
        menuEdit.add(menuItemUndo);
        menuItemRedo = new JMenuItem("Redo");
        menuItemRedo.addActionListener(this::menuItemRedoActionPerformed);
        menuEdit.add(menuItemRedo);
        menuEdit.addSeparator();
        menuItemDeleteSelected = new JMenuItem("Delete Selection");
        menuItemDeleteSelected.addActionListener(this::menuItemDeleteSelectedActionPerformed);
        menuEdit.add(menuItemDeleteSelected);
        menu.add(menuEdit);

        menuView = new JMenu("View");
        menuItemZoomIn = new JMenuItem("Zoom In");
        menuItemZoomIn.addActionListener(this::menuItemZoomInActionPerformed);
        menuView.add(menuItemZoomIn);
        menuItemZoomOut = new JMenuItem("Zoom Out");
        menuItemZoomOut.addActionListener(this::menuItemZoomOutActionPerformed);
        menuView.add(menuItemZoomOut);
        menuItemToggleGrid = new JMenuItem("Toggle Grid");
        menuItemToggleGrid.addActionListener(this::menuItemToggleGridActionPerformed);
        menuView.add(menuItemToggleGrid);

        menuView.addSeparator();
        menuViewShift = new JMenu("Shift..");
        menuItemShiftLeft = new JMenuItem("Left");
        menuViewShift.add(menuItemShiftLeft);
        menuItemShiftLeft.addActionListener(this::menuItemShiftLeftActionPerformed);
        menuItemShiftUp = new JMenuItem("Up");
        menuItemShiftUp.addActionListener(this::menuItemShiftUpActionPerformed);
        menuViewShift.add(menuItemShiftUp);
        menuItemShiftDown = new JMenuItem("Down");
        menuItemShiftDown.addActionListener(this::menuItemShiftDownActionPerformed);
        menuViewShift.add(menuItemShiftDown);
        menuItemShiftRight = new JMenuItem("Right");
        menuItemShiftRight.addActionListener(this::menuItemShiftRightActionPerformed);
        menuViewShift.add(menuItemShiftRight);

        menuView.add(menuViewShift);
        menu.add(menuView);

        menuTools = new JMenu("Tools");
        bgToolMode = new ButtonGroup();
        rmenuItemBuildMode = new JRadioButtonMenuItem("Build Mode");
        rmenuItemBuildMode.setSelected(true);
        rmenuItemBuildMode.addActionListener(this::rmenuItemBuildModeActionPerformed);
        bgToolMode.add(rmenuItemBuildMode);
        menuTools.add(rmenuItemBuildMode);
        rmenuItemSelectMode = new JRadioButtonMenuItem("Select Mode");
        rmenuItemSelectMode.addActionListener(this::rmenuItemSelectModeActionPerformed);
        bgToolMode.add(rmenuItemSelectMode);
        menuTools.add(rmenuItemSelectMode);
        menuTools.addSeparator();
        menuItemTestProject = new JMenuItem("Test Project");
        menuItemTestProject.addActionListener(this::menuItemTestProjectActionPerformed);
        menuTools.add(menuItemTestProject);
        menu.add(menuTools);

        menuItemCloseProject.setEnabled(false);
        menuItemSave.setEnabled(false);
        menuItemUndo.setEnabled(false);
        menuItemRedo.setEnabled(false);
        //menuItemDeleteSelected.setEnabled(false);
        //menuItemTestProject.setEnabled(false);

        setJMenuBar(menu);

        new Thread(this).start();
        setVisible(true);
    }

    //Menu File
    public void menuItemNewActionPerformed(ActionEvent e) {
        System.out.println("File > New");
    }

    public void menuItemOpenActionPerformed(ActionEvent e) {
        System.out.println("File > Open");
    }

    public void menuItemCloseProjectActionPerformed(ActionEvent e) {
        System.out.println("File > Close Project");
    }

    public void menuItemSettingsActionPerformed(ActionEvent e) {
        System.out.println("File > Settings");
    }

    public void menuItemSaveActionPerformed(ActionEvent e) {
        System.out.println("File > Save");
    }

    public void menuItemSaveAsActionPerformed(ActionEvent e) {
        System.out.println("File > Save As");
    }

    //Menu Edit
    public void menuItemUndoActionPerformed(ActionEvent e) {
        System.out.println("Edit > Undo");
    }

    public void menuItemRedoActionPerformed(ActionEvent e) {
        System.out.println("Edit > Redo");
    }

    public void menuItemDeleteSelectedActionPerformed(ActionEvent e) {
        System.out.println("Edit > Delete Selection");
    }

    //Menu View
    public void menuItemZoomInActionPerformed(ActionEvent e) {
        System.out.println("View > Zoom In");
    }

    public void menuItemZoomOutActionPerformed(ActionEvent e) {
        System.out.println("View > Zoom Out");
    }

    //Menu View Shift
    public void menuItemShiftLeftActionPerformed(ActionEvent e) {
        System.out.println("View > Shift > Left");
    }

    public void menuItemShiftUpActionPerformed(ActionEvent e) {
        System.out.println("View > Shift > Up");
    }

    public void menuItemShiftDownActionPerformed(ActionEvent e) {
        System.out.println("View > Shift > Down");
    }

    public void menuItemShiftRightActionPerformed(ActionEvent e) {
        System.out.println("View > Shift > Right");
    }

    public void menuItemToggleGridActionPerformed(ActionEvent e) {
        ((EditorPanel) panel).toggleGrid();
    }

    //Menu Tools
    public void rmenuItemBuildModeActionPerformed(ActionEvent e) {
        System.out.println("Tools > Build Mode");
        if(panel instanceof EditorPanel) ((EditorPanel) panel).getListener().setMode(EditorMouseInputListener.BUILD);
    }

    public void rmenuItemSelectModeActionPerformed(ActionEvent e) {
        System.out.println("Tools > Select Mode");
        if(panel instanceof EditorPanel) ((EditorPanel) panel).getListener().setMode(EditorMouseInputListener.SELECT);
    }

    public void menuItemTestProjectActionPerformed(ActionEvent e) {
        System.out.println("Tools > Test Project");

        if(panel instanceof EditorPanel) {
            if(((EditorPanel) panel).getActors().isEmpty()) {
                JOptionPane.showConfirmDialog(this, "There is nothing to test!", "Operation Not Possible", JOptionPane.OK_CANCEL_OPTION);
                return;
            }
        }

        //GameFrame gf = new GameFrame("Project Test - Random Heroes " + FrameManager.RH_VERSION + " Scene Editor " + FrameManager.SE_VERSION + " @crudemandarin", new Dimension(650, 300));
        //gf.setLocationRelativeTo(this);
        //gf.init();
    }
}