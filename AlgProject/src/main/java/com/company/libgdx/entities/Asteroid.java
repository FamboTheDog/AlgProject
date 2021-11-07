package com.company.libgdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.physics.box2d.Body;
import com.company.libgdx.screens.GameScreen;
import lombok.Getter;

import java.util.Arrays;
import java.util.Random;

public class Asteroid implements GameObject {

    private Body body;

    @Getter private float x;
    @Getter private float y;

    private GameScreen gameScreen;

    private Texture texture;

    float[] vertices;

    public Asteroid(String toParse) {
        toParse = toParse.substring(1, toParse.length() - 1);
        String[] points = toParse.split(", ");
        this.vertices = new float[points.length];
        for (int i = 0; i < points.length; i++) {
            vertices[i] = Float.parseFloat(points[i]);
        }
    }

//    public Asteroid(float x, float y, GameScreen gameScreen){
//        this.x = x;
//        this.y = y;
//        this.texture = new Texture("white.png");
//        this.gameScreen = gameScreen;
//        // this.body = BodyHelper.createBody(x, y, PLAYER_SIZE, PLAYER_SIZE, false
//        //        , 10000, gameScreen.getWorld(), ContactType.PLAYER);
//        Random rng = new Random();
//
//        int verticesCount = rng.nextInt(3) + 5;
//        // int verticesCount = 6;
//        final int maxLineLength = 30 * verticesCount / 2;
//        this.vertices = new float[verticesCount * 2];
//
//        float radius = rng.nextFloat(30,80);
//
//        int startPointX = rng.nextInt(50,550);
//        int startPointY = rng.nextInt(50,550);
//
//        for (int i = 1, j = 0; j < vertices.length; i++, j+=2) {
//            float previousX = startPointX + (float) (Math.sin(i)*(radius / rng.nextFloat(1,3)));
//            float previousY = startPointY + (float) (Math.cos(i)*(radius / rng.nextFloat(1,3)));
//            vertices[j] = previousX;
//            vertices[j + 1] = previousY;
//        }
//
//        Polygon polygon = new Polygon(vertices);
//        System.out.println(Arrays.toString(vertices));
//    }

    @Override
    public void update() {

    }

    ShapeRenderer renderer = new ShapeRenderer();
    {
        renderer.setAutoShapeType(true);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.end();
        renderer.begin();
        renderer.polygon(vertices);
        renderer.end();
        batch.begin();
    }

}