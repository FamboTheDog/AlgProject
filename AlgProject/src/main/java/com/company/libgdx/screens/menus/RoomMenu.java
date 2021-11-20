package com.company.libgdx.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.libgdx.screens.Boot;
import com.company.libgdx.screens.custom.CustomScreenAdapter;
import com.company.libgdx.screens.screens.CreateRoom;
import com.company.libgdx.screens.screens.JoinRoom;
import com.company.libgdx.util.Styles;
import lombok.Getter;
import lombok.SneakyThrows;

public class RoomMenu extends CustomScreenAdapter {

    @Getter private final Boot parent;
    private static final Stage stage = new Stage(new ScreenViewport());

    @Getter private CreateRoom createRoomScreen;
    @Getter private JoinRoom joinRoomScreen;

    public RoomMenu(Boot parent) {
        super(stage);
        this.parent = parent;
        this.createRoomScreen = new CreateRoom(this);
        this.joinRoomScreen = new JoinRoom(this);
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
                parent.setScreen(createRoomScreen);
            }
        });

        joinRoom.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(joinRoomScreen);
            }
        });

    }

}