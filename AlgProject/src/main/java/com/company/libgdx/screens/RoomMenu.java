package com.company.libgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.libgdx.util.Styles;
import lombok.SneakyThrows;
import net.java.games.input.Component;

public class RoomMenu extends ScreenAdapter {

    private final Boot parent;
    private final Stage stage;

    public RoomMenu(Boot parent) {
        this.parent = parent;
        stage = new Stage(new ScreenViewport());
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton createRoom = new TextButton("Create room", Styles.getButtonStyle());
        TextButton joinRoom = new TextButton("Join room", Styles.getButtonStyle());
        TextButton exit = new TextButton("Exit", Styles.getButtonStyle());

        table.add(createRoom).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(joinRoom).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        exit.addListener(new ChangeListener() {
            @SneakyThrows
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                UserCommunicationProtocol.closeConnection();
                Gdx.app.exit();
            }
        });

        createRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(parent.getCreateRoom());
            }
        });

        joinRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(parent.getJoinRoom());
            }
        });

    }

    @Override
    public void render(float delta) {
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}