package com.company.libgdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.company.libgdx.screens.GameScreen;
import com.company.libgdx.util.BodyHelper;
import com.company.libgdx.util.Constants;
import com.company.libgdx.util.ContactType;
import com.company.multiplayer.ServerConnection;
import lombok.Getter;

import java.io.PrintWriter;

public class Player implements GameObject {

    private Body body;

    private float moveSpeed = 4;
    @Getter private float angle = 0f;

    @Getter private float x;
    @Getter private float y;

    final float PLAYER_SIZE = 30;

    private final PrintWriter writer;

    private long lastShotTime = 0;

    GameScreen gameScreen;

    private final static float DEFAULT_SPAWN_POINT = 150;
    public Player(GameScreen gameScreen){
        this(DEFAULT_SPAWN_POINT, DEFAULT_SPAWN_POINT, gameScreen);
    }

    private Texture texture;
    public Player(float x, float y, GameScreen gameScreen){
        this.x = x;
        this.y = y;
        this.writer = ServerConnection.getOutputStream();
        this.texture = new Texture("white.png");
        this.gameScreen = gameScreen;
        this.body = BodyHelper.createBody(x, y, PLAYER_SIZE, PLAYER_SIZE, false
                , 10000, gameScreen.getWorld(), ContactType.PLAYER);
        velocity = new Vector2();
    }

    Vector2 velocity;

    public void moveUp(){
        velocity.set(0, moveSpeed);
        velocity.setAngleDeg(angle - 270);

    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.getPPM() - (PLAYER_SIZE / 2);
        y = body.getPosition().y * Constants.getPPM() - (PLAYER_SIZE / 2);
        velocity.set(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) angle += 5;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) angle -= 5;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) moveUp();

        body.setLinearVelocity(velocity);

        String playerPosition = getX() + " " + getY() + " " + getAngle();
        writer.println(playerPosition);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, PLAYER_SIZE / 2, PLAYER_SIZE / 2, PLAYER_SIZE, PLAYER_SIZE, 1f, 1f, angle, 0, 0, 32, 32, false, false);
    }

}
