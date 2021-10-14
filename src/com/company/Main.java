package com.company;

import com.company.gameloop.GameLoop;
import com.company.gameloop.RenderLayer;
import com.company.gameloop.Updater;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static String GAME_NAME = "Battleships";

    public static void main(String[] args) {
        JFrame window = new JFrame("Battleships");
        window.setSize(new Dimension(640,480));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setVisible(true);

        RenderLayer render = new RenderLayer();
        Updater updater = new Updater();
        GameLoop gameLoop = new GameLoop(render, updater);
        gameLoop.startLoop();

        window.add(render);
        window.revalidate();
        window.repaint();
    }

}