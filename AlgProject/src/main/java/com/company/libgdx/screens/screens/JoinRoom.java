package com.company.libgdx.screens.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.libgdx.screens.Boot;
import com.company.libgdx.screens.custom.CustomScreenAdapter;
import com.company.libgdx.screens.menus.RoomMenu;
import com.company.libgdx.util.Styles;
import lombok.SneakyThrows;


public class JoinRoom extends CustomScreenAdapter {

    private final RoomMenu parent;
    private final Stage stage;
    private final Table table;

    private boolean noActiveRooms;

    public JoinRoom(RoomMenu parent) {
        super(parent);
        this.parent = parent;
        this.stage = super.getStage();
        table = new Table();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        stage.addActor(table);
        loadRooms();
    }

    public void loadRooms(){
        String[] servers = UserCommunicationProtocol.listRooms();
        if (servers[0].equals("EMPTY")) {
            if (noActiveRooms) return;
            table.add(new Label("There are currently no active rooms. Would you like to create one?",
                    Styles.getLabelStyle()));
            TextButton createServerInstead = new TextButton("Create Room", Styles.getButtonStyle());
            createServerInstead.addListener(new ChangeListener() {
                @SneakyThrows
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    parent.getParent().setScreen(parent.getCreateRoomScreen());
                }
            });
            table.row();
            table.add(createServerInstead);
            noActiveRooms = true;
            return;
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
                    String serverResponse = UserCommunicationProtocol.joinRoom(actor.getName());
                    System.out.println("joined : " + serverResponse);
                    GameScreen gameScreen = new GameScreen(Boot.getBootInstance().getOrthographicCamera());
                    parent.getParent().setScreen(gameScreen);
                    gameScreen.startGame(serverResponse);
                    joinButton.removeListener(this);
                }
            });
            table.add(joinButton).uniformX();
            table.row();
        }
    }

}

