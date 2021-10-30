package com.company.libgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.company.libgdx.entities.Enemy;
import com.company.libgdx.entities.GameObject;
import com.company.libgdx.entities.Player;
import com.company.multiplayer.ServerConnection;
import lombok.Getter;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    @Getter private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.camera.position.set(new Vector3(Boot.getBootInstance().getScreenWidth() / 2f, Boot.getBootInstance().getScreenHeight() / 2f, 0));
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0,0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
    }

    public void update() {
        world.step(1/ 60f, 6, 2);
        batch.setProjectionMatrix(camera.combined);

        gameObjects.forEach(GameObject::update);

        String line;
        try {
            while (!(line = ServerConnection.getInputStream().readLine()).equals("END")) {
                String[] newPlayerPositions = line.split(" ");
                Enemy newPlayer = new Enemy(Float.parseFloat(newPlayerPositions[0]),
                        Float.parseFloat(newPlayerPositions[1]), Float.parseFloat(newPlayerPositions[2]), this);
                gameObjects.add(newPlayer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void render(float delta) {
        update();

        // clearing the screen
        Gdx.gl.glClearColor(0,0,0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        gameObjects.forEach(gameObject -> gameObject.draw(batch));
        batch.end();
    }

    public void startGame() {
        Player player = new Player(this);
        gameObjects.add(player);
    }
}
