package com.company.entities;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/*
* TODO
*  bullets need to disappear so they don't take up the memory for no reason
*  possible solutions:
*   - Delete bullets after a certain amount of updates
*   - Delete bullets when they get out of render distance (might be hard to do in the actual multiplayer)
* */
@AllArgsConstructor
public class Bullet implements RenderObject{

    double angle;
    double x;
    double y;

    final double WIDTH = 10;

    final double SPEED = 4;

    @Override
    public void update() {
        // when shooting, the angle should be saved to bullet class and the movement is then calculated in the same way as player's movement
        x += SPEED * Math.sin(angle);
        y += SPEED * -Math.cos(angle);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        RoundRectangle2D.Double bullet = new RoundRectangle2D.Double(x - WIDTH / 2, y - WIDTH / 2, WIDTH, WIDTH, WIDTH, WIDTH);
        g.fill(bullet);
    }
}
