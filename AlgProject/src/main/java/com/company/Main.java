package com.company;

import com.company.gameloop.GameLoop;
import com.company.gameloop.MPUpdater;
import com.company.gameloop.RenderLayer;
import com.company.gameloop.SPUpdater;
import com.company.menu.Menu;
import com.company.multiplayer.ServerConnection;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Main {

    private static String GAME_NAME = "Battleships";
    @Getter private static JPanel viewContainer = new JPanel();

    public static void main(String[] args) throws IOException {
        JFrame window = new JFrame(GAME_NAME);
        window.setSize(new Dimension(640,480));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setVisible(true);

        GameLoop gameLoop = new GameLoop(new RenderLayer(), new SPUpdater());
        GameLoop MPGameLoop = new GameLoop(new RenderLayer(), new MPUpdater());

        viewContainer.setLayout(new BorderLayout());
        window.add(viewContainer);

        viewContainer.add(new Menu(gameLoop, MPGameLoop, viewContainer), BorderLayout.CENTER);

        window.revalidate();
        window.repaint();
    }

}
