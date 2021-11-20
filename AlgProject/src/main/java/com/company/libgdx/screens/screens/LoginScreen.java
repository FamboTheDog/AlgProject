package com.company.libgdx.screens.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.company.communication.APICalls;
import com.company.communication.APIHandler;
import com.company.communication.GsonUtil;
import com.company.communication.ResponseException;
import com.company.libgdx.screens.Boot;
import com.company.libgdx.screens.custom.CustomScreenAdapter;
import com.company.libgdx.screens.menus.LoginMenu;
import com.company.libgdx.screens.menus.RoomMenu;
import com.company.libgdx.util.Styles;
import com.example.backend.db.User;
import com.example.backend.exceptions.ApiResponseError;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.lwjgl.opengl.GL;
import retrofit2.Call;
import retrofit2.Response;

public class LoginScreen extends CustomScreenAdapter {

    private final LoginMenu parent;
    private static final Stage stage = new Stage(new ScreenViewport());
    private final Table table;

    public LoginScreen(LoginMenu parent) {
        super(stage);
        this.parent = parent;
        table = new Table();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        stage.addActor(table);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label usernameText = new Label("Username: ", Styles.getLabelStyle());
        TextField username = new TextField("", Styles.getTextInputStyle());
        username.setMaxLength(24);
        username.setSelection(0,0);

        table.add(usernameText);
        table.add(username);
        table.row().pad(10, 0, 10, 0);

        Label passwordText = new Label("Password: ", Styles.getLabelStyle());
        TextField password = new TextField("", Styles.getTextInputStyle());
        password.setPasswordCharacter('*');
        password.setPasswordMode(true);
        password.setSelection(0,0);

        table.add(passwordText);
        table.add(password);
        table.row();

        GL.createCapabilities();

        TextButton login = new TextButton("Login", Styles.getButtonStyle());
        login.addListener(new ChangeListener() {
            @SneakyThrows
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                Call<User> loginCall = APIHandler.createService(APICalls.class)
                        .login(new User(username.getText(), password.getText()));
                Response<User> response = loginCall.execute();
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user == null) throw new ResponseException();

                    parent.getParent().setScreen(new RoomMenu(parent.getParent()));
                    parent.dispose();
                } else {
                    assert response.errorBody() != null;
                    ApiResponseError parsed = GsonUtil.parse(response.errorBody().string(), ApiResponseError.class);
                    System.out.println(parsed.getMessage());
                    parsed.getErrors().forEach(System.out::println);
                }

            }
        });

        table.add(login).center();
    }

}
