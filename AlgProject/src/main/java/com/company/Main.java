package com.company;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.libgdx.screens.Boot;
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

        UserCommunicationProtocol.initialize();

        new Lwjgl3Application(new Boot(), config);
    }

}
