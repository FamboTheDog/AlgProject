package com.company.libgdx.util;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.company.libgdx.screens.Boot;

public class StageUtil extends InputListener {

    public static Stage customStage(Screen onBackSpace) {
        Stage stage = new Stage(new ScreenViewport());
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    Boot.getBootInstance().setScreen(onBackSpace);
                }
                return true;
            }
        });
        return stage;
    }

}
