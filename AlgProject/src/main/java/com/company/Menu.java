package com.company;

import com.company.gameloop.GameLoop;
import com.company.multiplayer.ApiConnection;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    public Menu(GameLoop gameLoop, JPanel viewContainer) {

        this.setLayout(new FlowLayout());

        JButton startGame = new JButton("Single player");
        startGame.addActionListener(e->{
            gameLoop.startLoop();
            viewContainer.remove(this);
            viewContainer.add(gameLoop.getGraphics());
            viewContainer.revalidate();
            viewContainer.repaint();
        });

        JButton multiplayer = new JButton("Multiplayer");
        multiplayer.addActionListener(e->{
            ApiConnection.makeCall("test");
        });

        this.add(startGame);
        this.add(multiplayer);
    }

}
