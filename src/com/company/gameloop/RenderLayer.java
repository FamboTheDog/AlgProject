package com.company.gameloop;

import com.company.entities.Bullet;
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

    public void addKeyBinds(Player player) {
        // Movement
        createBind(KeyStroke.getKeyStroke('a'), "left", e -> player.setLeft(true));
        createBind(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('a'), 0, true), "leftStop", e -> player.setLeft(false));
        createBind(KeyStroke.getKeyStroke('d'), "right", e -> player.setRight(true));
        createBind(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('d'), 0, true), "rightStop", e -> player.setRight(false));
        createBind(KeyStroke.getKeyStroke('w'), "forward", e -> player.setUp(true));
        createBind(KeyStroke.getKeyStroke(KeyEvent.getExtendedKeyCodeForChar('w'), 0, true), "forwardStop", e -> player.setUp(false));

        //Others
        double bulletX = player.getX() + player.getHEIGHT() / 2 * Math.sin(player.getAngle());
        double bulletY = player.getY() + player.getHEIGHT() / 2 * -Math.cos(player.getAngle());
        createBind(KeyStroke.getKeyStroke("SPACE"), "shoot", e-> player.shoot(gameObjects));
    }

    private void createBind(KeyStroke key, String actionMapKey, ActionListener action){
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
