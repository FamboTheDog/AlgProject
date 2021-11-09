package com.company.libgdx.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import lombok.Getter;

public class Boot extends Game {

    @Getter private static Boot bootInstance;
    @Getter private int screenWidth, screenHeight;
    private OrthographicCamera orthographicCamera;
    @Getter private StartMenu startMenu;
    @Getter private CreateRoom createRoom;
    @Getter private GameScreen gameScreen;
    @Getter private JoinRoom joinRoom;

    public Boot(){
        bootInstance = this;
    }

    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, screenWidth, screenHeight);

        this.startMenu = new StartMenu(this);
        this.createRoom = new CreateRoom(this);
        this.gameScreen = new GameScreen(orthographicCamera);
        this.joinRoom = new JoinRoom(this);

        setScreen(startMenu);
    }

}
