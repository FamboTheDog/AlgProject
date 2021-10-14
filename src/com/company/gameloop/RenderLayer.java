package com.company.gameloop;

import com.company.entities.Player;
import com.company.entities.RenderObject;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class RenderLayer extends JPanel {

    @Setter
    private ArrayList<RenderObject> gameObjects;

    public RenderLayer(){
//        this.setPreferredSize(new Dimension(640,480));
//        movementBinds(KeyStroke.getKeyStroke('w'), "forward",  e -> loadedLevel.getPlayer().setUp(true));
//        movementBinds(KeyStroke.getKeyStroke('s'), "backward", e -> loadedLevel.getPlayer().setDown(true));
//        movementBinds(KeyStroke.getKeyStroke('a'), "left",     e -> loadedLevel.getPlayer().setLeft(true));
//        movementBinds(KeyStroke.getKeyStroke('d'), "right",    e -> loadedLevel.getPlayer().setRight(true));
//
//        movementBinds(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('w'), 0, true), "forwardStop",  e -> loadedLevel.getPlayer().upFalse());
//        movementBinds(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('s'), 0, true), "backwardStop", e -> loadedLevel.getPlayer().downFalse());
//        movementBinds(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('a'), 0, true), "leftStop",     e -> loadedLevel.getPlayer().leftFalse());
//        movementBinds(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('d'), 0, true), "rightStop",    e -> loadedLevel.getPlayer().rightFalse());
    }

    public void addKeyBinds(Player player) {
        final double TURN_SPEED = 0.1;
        movementBinds(KeyStroke.getKeyStroke('a'), "left", e -> player.setLeft(true));
        movementBinds(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('a'), 0, true), "leftStop", e -> player.setLeft(false));
        movementBinds(KeyStroke.getKeyStroke('d'), "right", e -> player.setRight(true));
        movementBinds(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('d'), 0, true), "rightStop", e -> player.setRight(false));
        movementBinds(KeyStroke.getKeyStroke('w'), "forward", e -> player.setUp(true));
        movementBinds(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('w'), 0, true), "forwardStop", e -> player.setUp(false));
    }

    private void movementBinds(KeyStroke key, String actionMapKey, ActionListener action){
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(key, actionMapKey);
        this.getActionMap().put(actionMapKey, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.actionPerformed(e);
            }
        });
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;
        gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        gd.setColor(Color.black);
        gd.fillRect(0, 0, getWidth(), getHeight());
        gameObjects.forEach(o -> o.paint(gd));
    }

}
