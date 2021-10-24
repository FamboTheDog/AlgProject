package com.company.entities;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

public class ConnectedPlayer implements RenderObject {

    @Setter @Getter double angle;
    @Setter@Getter double x;
    @Setter@Getter double y;


    public ConnectedPlayer(double x, double y, double angle){
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    @Override
    public void update() {

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