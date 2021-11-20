package com.company.libgdx.screens.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.company.libgdx.screens.Boot;
import com.company.libgdx.screens.custom.CustomScreenAdapter;
import com.company.libgdx.screens.screens.LoginScreen;
import com.company.libgdx.screens.screens.RegisterScreen;
import com.company.libgdx.util.Styles;
import lombok.Getter;

public class LoginMenu extends CustomScreenAdapter {

    @Getter private final Boot parent;
    private static final Stage stage = new Stage(new ScreenViewport());
    private final Table table;

    @Getter private LoginScreen loginScreen;
    @Getter private RegisterScreen registerScreen;

    public LoginMenu(Boot parent) {
        super(stage);
        this.parent = parent;
        table = new Table();
        this.loginScreen = new LoginScreen(this);
        this.registerScreen = new RegisterScreen(this);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        table.setFillParent(true);
        stage.addActor(table);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextButton login = new TextButton("Login", Styles.getButtonStyle());
        TextButton register = new TextButton("Register", Styles.getButtonStyle());
        TextButton exit = new TextButton("Exit", Styles.getButtonStyle());

        login.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(loginScreen);
            }
        });

        register.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.setScreen(registerScreen);
            }
        });

        table.add(login).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(register).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();
    }

}
