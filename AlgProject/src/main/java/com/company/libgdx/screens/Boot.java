package com.company.libgdx.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.company.libgdx.screens.menus.LoginMenu;
import com.company.libgdx.screens.menus.RoomMenu;
import com.company.libgdx.screens.screens.*;
import lombok.Getter;

import java.util.Optional;

public class Boot extends Game {

    @Getter private static Boot bootInstance;
    @Getter private int screenWidth, screenHeight;
    @Getter private OrthographicCamera orthographicCamera;

    @Getter private GameScreen gameScreen;
    @Getter private LoginMenu loginMenu;

    public Boot(){
        bootInstance = this;
    }

    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, screenWidth, screenHeight);

        this.loginMenu = new LoginMenu(this);

        setScreen(loginMenu);
    }

}
