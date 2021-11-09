package com.company.libgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.libgdx.util.Styles;
import lombok.SneakyThrows;

import java.awt.*;

import static java.awt.Color.BLACK;
import static java.awt.Cursor.DEFAULT_CURSOR;

public class CreateRoom extends ScreenAdapter {

    private Boot parent;
    private Stage stage;

    public CreateRoom(Boot parent) {
        this.parent = parent;
        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextField.TextFieldStyle style = Styles.getTextInputStyle();
        // getting cursor for text field
        Label oneCharSizeCalibrationThrowAway = new Label("|", Styles.getLabelStyle());
        Pixmap cursorColor = new Pixmap((int) oneCharSizeCalibrationThrowAway.getWidth(),
                (int) new TextField("",Styles.getTextInputStyle()).getHeight(),
                Pixmap.Format.RGB888);
        cursorColor.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        cursorColor.fill();
        style.cursor = new Image(new Texture(cursorColor)).getDrawable();

        TextField serverName = new TextField("", style);
        serverName.setTextFieldFilter((textField, c) -> c != ' ');
        serverName.setSelection(0,0);

        Label serverNameLabel = new Label("Server name: ", Styles.getLabelStyle());
        TextButton startRoom = new TextButton("Create room", Styles.getButtonStyle());

        table.add(serverNameLabel);
        table.add(serverName);
        table.row();
        table.add(startRoom).center();

        startRoom.addListener(new ChangeListener() {
            @SneakyThrows
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startRoom.removeListener(startRoom.getClickListener());
                String serverResponse = UserCommunicationProtocol.createRoom(serverName.getText());
                parent.getGameScreen().startGame(serverResponse);
                parent.setScreen(parent.getGameScreen());
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
        // change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        // dispose of assets when not needed anymore
        stage.dispose();
    }

}
