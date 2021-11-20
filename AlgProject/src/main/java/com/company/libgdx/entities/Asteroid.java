package com.company.libgdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.company.libgdx.screens.screens.GameScreen;
import lombok.Getter;

public class Asteroid implements GameObject {

    private Body body;

    @Getter private float x;
    @Getter private float y;

    private GameScreen gameScreen;

    private Texture texture;

    private final float[] vertices;

    private final ShapeRenderer renderer = new ShapeRenderer();

    public Asteroid(String toParse) {
        toParse = toParse.substring(1, toParse.length() - 1);
        String[] points = toParse.split(", ");
        this.vertices = new float[points.length];
        for (int i = 0; i < points.length; i++) {
            vertices[i] = Float.parseFloat(points[i]);
        }
        renderer.setAutoShapeType(true);
    }

    @Override
    public void update() {

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