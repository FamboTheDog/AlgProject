package com.company.libgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.company.communication_protocol.user.UserCommunicationProtocol;
import com.company.libgdx.entities.*;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    @Getter private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;

    private final ArrayList<GameObject> gameObjects = new ArrayList<>();

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
            while (!(line = UserCommunicationProtocol.getInputStream().readLine()).equals("END")) {
                if (line.startsWith("BULLET")) {
                    String[] bulletPosition = line.split(UserCommunicationProtocol.PARAMETER_SEPARATOR);
                    gameObjects.add(new Bullet(Float.parseFloat(bulletPosition[1]), Float.parseFloat(bulletPosition[2])));
                } else {

                    String[] newPlayerPositions = line.split(" ");
                    Enemy newPlayer = new Enemy(Float.parseFloat(newPlayerPositions[0]),
                            Float.parseFloat(newPlayerPositions[1]), Float.parseFloat(newPlayerPositions[2]), this);
                    gameObjects.add(newPlayer);
                }
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
        Gdx.gl.glClear(com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT | com.badlogic.gdx.graphics.GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));

        batch.begin();
        gameObjects.forEach(gameObject -> gameObject.draw(batch));
        batch.end();
    }

    /**
     * Recreates the game state from the serverCommands
     * @param serverCommands Server response that needs to be parsed into game objects
     */
    public void startGame(String serverCommands) {
        // this is incredibly ugly, should be refactored, but I'm to lazy for it now
        System.out.println(serverCommands);
        System.out.println("start game was called");

        String[] individualPositions = serverCommands.split(UserCommunicationProtocol.COMMAND_SEPARATOR);
        String[] playerPositions = individualPositions[0].split(UserCommunicationProtocol.PARAMETER_SEPARATOR);
        Player player = new Player(Float.parseFloat(playerPositions[0]), Float.parseFloat(playerPositions[1]),
                Float.parseFloat(playerPositions[2]), this);
        gameObjects.add(player);

        String[] asteroids;
        if (individualPositions.length > 2) {
            String[] enemyPosition = individualPositions[1].split(UserCommunicationProtocol.PARAMETER_SEPARATOR);
            Enemy enemy = new Enemy(Float.parseFloat(enemyPosition[0]), Float.parseFloat(enemyPosition[1]),
                    Float.parseFloat(enemyPosition[2]), this);
            gameObjects.add(enemy);
            asteroids = individualPositions[2].split(UserCommunicationProtocol.PARAMETER_SEPARATOR);
        } else {
            asteroids = individualPositions[1].split(UserCommunicationProtocol.PARAMETER_SEPARATOR);
        }

        for (String asteroid : asteroids) {
            gameObjects.add(new Asteroid(asteroid));
        }
    }
}
