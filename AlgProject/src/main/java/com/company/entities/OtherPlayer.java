package com.company.entities;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.io.BufferedReader;
import java.io.IOException;

public class OtherPlayer implements RenderObject {

    @Setter @Getter private double angle;
    @Setter @Getter private double x;
    @Setter @Getter private double y;

    private BufferedReader reader;

    public OtherPlayer(double x, double y, double angle, BufferedReader reader){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.reader = reader;
    }

    @Override
    public void update() {
        try {
            String[] newPositions = reader.readLine().split(" ");
            this.x = Double.parseDouble(newPositions[0]);
            this.y = Double.parseDouble(newPositions[1]);
            this.angle = Double.parseDouble(newPositions[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Getter final double HEIGHT = 20;
    final double PLAYER_WIDTH = Math.sqrt(HEIGHT * HEIGHT +
            HEIGHT * HEIGHT);

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.white);
        // g.rotate(angle, x, y);
        AffineTransform beforeRotation = g.getTransform();
        AffineTransform rotated = g.getTransform();
        rotated.rotate(angle, x, y);
        g.setTransform(rotated);

        // Player's shape is a triangle, sadly you can't make a polygon with double values, so I used this approach instead
        Path2D player = new Path2D.Double();
        player.moveTo(x, y - (HEIGHT / 2));
        player.lineTo(x - PLAYER_WIDTH / 2, y + HEIGHT);
        player.lineTo(x + PLAYER_WIDTH / 2 + 1, y + HEIGHT); // For some reason, this had an offset of 1 px, so I just lazily fixed it with + 1
        player.lineTo(x, y - (HEIGHT / 2));
        player.closePath();
        g.draw(player);

        g.setTransform(beforeRotation);
    }

}