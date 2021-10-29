package com.company;

import com.company.gameloop.GameLoop;
import com.company.gameloop.Updater;
import com.company.gameloop.RenderLayer;
import com.company.menu.Menu;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class Main {

    @Getter private static final JPanel viewContainer = new JPanel();

    public static void main(String[] args) {
        final String GAME_NAME = "Battleships";
        JFrame window = new JFrame(GAME_NAME);
        window.setSize(new Dimension(640,480));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setVisible(true);

        GameLoop MPGameLoop = new GameLoop(new RenderLayer(), new Updater());

        viewContainer.setLayout(new BorderLayout());
        window.add(viewContainer);

        viewContainer.add(new Menu(MPGameLoop, viewContainer), BorderLayout.CENTER);

        window.revalidate();
        window.repaint();
    }

}
