package com.company.server_structure.room.entities.entity_creators;

import java.util.Arrays;
import java.util.Random;

public class Asteroids {

    private static final Random rng = new Random();

    public static String createAsteroid() {
        int verticesCount = rng.nextInt(3) + 5;

        int startPointX = rng.nextInt(400) + 50;
        int startPointY = rng.nextInt(400) + 50;

        float maxRadius = rng.nextInt(30) + 30 + rng.nextFloat();
        float minRadius = maxRadius / 1.5f;


        float[] vertices = new float[verticesCount * 2];

        float previousX;
        float previousY;

        int invertedPointChance = rng.nextInt((verticesCount * 2) - 1) + 1; // 50% chance for an inverted point
        final int MAX_INVERTED_POINT_LENGTH = 8; // after a bit of testing, I came to the conclusion, that 8 is a good number
        for (int i = 1, j = 0; j < vertices.length; i++, j+=2) {
            if(i == invertedPointChance) {
                previousX = startPointX - (float) (Math.sin(i) * ((rng.nextInt((int) (maxRadius - minRadius)) + minRadius) / MAX_INVERTED_POINT_LENGTH));
                previousY = startPointY - (float) (Math.cos(i) * ((rng.nextInt((int) (maxRadius - minRadius)) + minRadius) / MAX_INVERTED_POINT_LENGTH));
            }else {
                previousX = startPointX + (float) (Math.sin(i) * ((rng.nextInt((int) (maxRadius - minRadius)) + minRadius)));
                previousY = startPointY + (float) (Math.cos(i) * ((rng.nextInt((int) (maxRadius - minRadius)) + minRadius)));
            }
            vertices[j] = previousX;
            vertices[j + 1] = previousY;
        }
        return Arrays.toString(vertices);
    }
}
