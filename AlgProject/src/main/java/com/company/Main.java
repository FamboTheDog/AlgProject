package com.company;

import com.company.gameloop.GameLoop;
import com.company.gameloop.RenderLayer;
import com.company.gameloop.Updater;
import com.company.menu.Menu;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static String GAME_NAME = "Battleships";

    public static void main(String[] args) {
        JFrame window = new JFrame(GAME_NAME);
        window.setSize(new Dimension(640,480));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setVisible(true);

        RenderLayer render = new RenderLayer();
        Updater updater = new Updater();
        GameLoop gameLoop = new GameLoop(render, updater);

        JPanel viewContainer = new JPanel();
        viewContainer.setLayout(new BorderLayout());
        window.add(viewContainer);

        viewContainer.add(new Menu(gameLoop, viewContainer), BorderLayout.CENTER);

        window.revalidate();
        window.repaint();
    }

}
