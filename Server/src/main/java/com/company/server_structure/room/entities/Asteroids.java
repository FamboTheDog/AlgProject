package com.company.server_structure.room.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Asteroids {

    private static final Random rng = new Random();

    public static String createAsteroid() {
        int verticesCount = rng.nextInt(3) + 5;

        final int maxLineLength = 30 * verticesCount / 2;
        float[] vertices = new float[verticesCount * 2];

        float radius = rng.nextFloat(30,80);

        int startPointX = rng.nextInt(50,550);
        int startPointY = rng.nextInt(50,550);

        for (int i = 1, j = 0; j < vertices.length; i++, j+=2) {
            float previousX = startPointX + (float) (Math.sin(i) * (radius / rng.nextFloat(1,3)));
            float previousY = startPointY + (float) (Math.cos(i) * (radius / rng.nextFloat(1,3)));
            vertices[j] = previousX;
            vertices[j + 1] = previousY;
        }
        return Arrays.toString(vertices);
    }
}
