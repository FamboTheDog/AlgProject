package com.company;

import com.company.gameloop.GameLoop;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;

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
            try {
                ServerSocket server = new ServerSocket();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        this.add(startGame);
    }

}
