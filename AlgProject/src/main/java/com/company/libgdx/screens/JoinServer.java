package com.company.libgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.company.libgdx.util.Styles;
import com.company.multiplayer.ServerConnection;
import lombok.SneakyThrows;


public class JoinServer extends ScreenAdapter {

    private Boot parent;
    private Stage stage;
    private Table table;

    public JoinServer(Boot parent) {
        this.parent = parent;
        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());
        table = new Table();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen. Everything else will go inside this table.
        table.setFillParent(true);
        stage.addActor(table);

        loadRooms();
    }

    public void loadRooms(){
        String[] servers = ServerConnection.listRooms();
        if (servers[0].equals("EMPTY")) {
            System.out.println("shit's empty");
        }

        table.clearChildren();

        for (String name: servers) {
            System.out.println(name);
            table.add(new Label(name, Styles.getLabelStyle())).uniformX();
            TextButton joinButton = new TextButton("Join", Styles.getButtonStyle());
            joinButton.setName(name);
            joinButton.addListener(new com.badlogic.gdx.scenes.scene2d.utils.ChangeListener() {
                @SneakyThrows
                @Override
                public void changed(ChangeEvent changeEvent, Actor actor) {
                    System.out.println(actor.getName());
                    ServerConnection.joinRoom(actor.getName());
                    parent.getGameScreen().startGame();
                    parent.setScreen(parent.getGameScreen());
                }
            });
            table.add(joinButton).uniformX();
            table.row();
        }
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

