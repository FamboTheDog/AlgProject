package com.company;

import com.company.gameloop.GameLoop;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {

    // private GameLoop gameLoop;

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

        this.add(startGame);
    }

}
