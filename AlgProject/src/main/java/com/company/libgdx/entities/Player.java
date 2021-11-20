package com.company.libgdx.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.company.communication_protocol.PlayerSize;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.libgdx.screens.screens.GameScreen;
import com.company.libgdx.util.BodyHelper;
import com.company.libgdx.util.Constants;
import com.company.libgdx.util.ContactType;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Player implements GameObject {

    private final Body body;

    private final float moveSpeed = 4;
    @Getter private float angle;

    @Getter private float x;
    @Getter private float y;

    public static final float PLAYER_SIZE = PlayerSize.PLAYER_SIZE;

    private final PrintWriter writer;
    private final BufferedReader reader;

    private final GameScreen gameScreen;

    private final Texture texture;
    public Player(float x, float y, float angle, GameScreen gameScreen){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.writer = UserCommunicationProtocol.getOutputStream();
        this.reader = UserCommunicationProtocol.getInputStream();
        this.texture = new Texture("white.png");
        this.texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.gameScreen = gameScreen;
        this.body = BodyHelper.createBody(x, y, PLAYER_SIZE, PLAYER_SIZE, false
                , 10000, gameScreen.getWorld(), ContactType.PLAYER);
        velocity = new Vector2();
    }

    private final Vector2 velocity;

    public void moveUp(){
        velocity.set(0, moveSpeed);
        velocity.setAngleDeg(angle - 270);
    }

    @Override
    public void update() {
        x = body.getPosition().x * Constants.getPPM() - (PLAYER_SIZE / 2);
        y = body.getPosition().y * Constants.getPPM() - (PLAYER_SIZE / 2);
        velocity.set(0, 0);

        String moves = "";
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            angle += 5;
            moves += "LEFT;";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            angle -= 5;
            moves += "RIGHT;";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp();
            moves += "FORWARD;";
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            // todo make client side shooting
            moves += "SHOOT;";
        }

        if (moves.length() > 0) moves = moves.substring(0, moves.length() - 1);

        body.setLinearVelocity(velocity);

        // String playerPosition = getX() + " " + getY() + " " + getAngle();
        writer.println(moves);

        try{
            String newPosition = reader.readLine();
            System.out.println(newPosition);
            String[] newPlayerPosition = newPosition.split(" ");
            this.x = Float.parseFloat(newPlayerPosition[0]);
            this.y = Float.parseFloat(newPlayerPosition[1]);
            this.angle = Float.parseFloat(newPlayerPosition[2]);
        } catch (IOException ignored) {}
        System.out.println("3");
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, PLAYER_SIZE / 2, PLAYER_SIZE / 2, PLAYER_SIZE, PLAYER_SIZE, 1f, 1f, angle, 0, 0, 32, 32, false, false);
    }

}
