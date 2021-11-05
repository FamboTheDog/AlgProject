package com.company;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.company.libgdx.screens.Boot;
import com.company.multiplayer.ServerConnection;
import lombok.Getter;

import javax.swing.*;


public class Main {

    @Getter private static final JPanel viewContainer = new JPanel();

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setIdleFPS(60);
        config.useVsync(true);
        final String GAME_NAME = "Battleships";
        config.setTitle(GAME_NAME);

        config.setWindowedMode(640,480);

        ServerConnection.initialize();

        new Lwjgl3Application(new Boot(), config);

//        JFrame window = new JFrame(GAME_NAME);
//        window.setSize(new Dimension(640,480));
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setLocationRelativeTo(null);
//        window.setResizable(true);
//        window.setVisible(true);
//
//        GameLoop MPGameLoop = new GameLoop(new RenderLayer(), new Updater());
//
//        viewContainer.setLayout(new BorderLayout());
//        window.add(viewContainer);
//
//        viewContainer.add(new Menu(MPGameLoop, viewContainer), BorderLayout.CENTER);
//
//        window.revalidate();
//        window.repaint();
    }

}
