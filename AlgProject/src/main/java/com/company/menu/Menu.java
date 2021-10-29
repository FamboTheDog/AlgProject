package com.company.menu;

import com.company.gameloop.GameLoop;
import com.company.multiplayer.ServerConnection;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    public Menu(GameLoop MPGameLoop, JPanel viewContainer) {

        this.setLayout(new FlowLayout());

        JButton multiplayer = new JButton("Multiplayer");
        multiplayer.addActionListener(e->{
            ServerConnection.initialize();
            viewContainer.remove(this);
            viewContainer.add(new ServerMenu(MPGameLoop));
            viewContainer.revalidate();
            viewContainer.repaint();
        });

        JButton quitGame = new JButton("Quit game");
        quitGame.addActionListener(e-> System.exit(0));

        this.add(multiplayer);
        this.add(quitGame);
    }

}
