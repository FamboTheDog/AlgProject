package com.company.libgdx.screens.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.libgdx.screens.Boot;
import com.company.libgdx.screens.custom.CustomScreenAdapter;
import com.company.libgdx.screens.menus.RoomMenu;
import com.company.libgdx.util.Styles;
import lombok.SneakyThrows;

public class CreateRoom extends CustomScreenAdapter {

    private final RoomMenu parent;
    private final Stage stage;

    public CreateRoom(RoomMenu parent) {
        super(parent);
        this.parent = parent;
        this.stage = super.getStage();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextField serverName = new TextField("", Styles.getTextInputStyle());
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
                GameScreen gameScreen = new GameScreen(Boot.getBootInstance().getOrthographicCamera());
                parent.getParent().setScreen(gameScreen);
                gameScreen.startGame(serverResponse);
            }
        });

    }

}
