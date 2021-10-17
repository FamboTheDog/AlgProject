package com.company.menu;

import com.company.gameloop.GameLoop;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    public Menu(GameLoop gameLoop, GameLoop MPGameLoop, JPanel viewContainer) {

        this.setLayout(new FlowLayout());

        JButton startGame = new JButton("Single player");
        startGame.addActionListener(e->{
            viewContainer.remove(this);
            viewContainer.add(gameLoop.getGraphics());
            gameLoop.startLoop();
            viewContainer.revalidate();
            viewContainer.repaint();
        });

        JButton multiplayer = new JButton("Multiplayer");
        multiplayer.addActionListener(e->{
            viewContainer.remove(this);
            viewContainer.add(new ServerMenu(MPGameLoop));
            viewContainer.revalidate();
            viewContainer.repaint();
        });

        JButton quitGame = new JButton("Quit game");
        quitGame.addActionListener(e-> System.exit(0));

        this.add(startGame);
        this.add(multiplayer);
        this.add(quitGame);
    }

}
