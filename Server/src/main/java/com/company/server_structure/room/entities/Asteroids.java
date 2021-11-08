package com.company.server_structure.room.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Asteroids {

    private static final Random rng = new Random();

    public static String createAsteroid() {
        int verticesCount = rng.nextInt(3) + 5;

        int startPointX = rng.nextInt(50,450);
        int startPointY = rng.nextInt(50,450);

        float maxRadius = rng.nextFloat(30,60);
        float minRadius = maxRadius / 1.5f;


        float[] vertices = new float[verticesCount * 2];

        float previousX;
        float previousY;

        int invertedPointChance = rng.nextInt(0, verticesCount * 2); // 50% chance for an inverted point
        final int MAX_INVERTED_POINT_LENGTH = 8; // after a bit of testing, I came to the conclusion, that 8 is a good number
        for (int i = 1, j = 0; j < vertices.length; i++, j+=2) {
            if(i == invertedPointChance) {
                previousX = startPointX - (float) (Math.sin(i) * (rng.nextFloat(minRadius, maxRadius) / MAX_INVERTED_POINT_LENGTH));
                previousY = startPointY - (float) (Math.cos(i) * (rng.nextFloat(minRadius, maxRadius) / MAX_INVERTED_POINT_LENGTH));
            }else {
                previousX = startPointX + (float) (Math.sin(i) * (rng.nextFloat(minRadius, maxRadius)));
                previousY = startPointY + (float) (Math.cos(i) * (rng.nextFloat(minRadius, maxRadius)));
            }
            vertices[j] = previousX;
            vertices[j + 1] = previousY;
        }
        return Arrays.toString(vertices);
    }
}
