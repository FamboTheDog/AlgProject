package com.company.entities;


import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
/*
* TODO
*  the player should have something like acceleration
* */
public class Player implements RenderObject {

    final double moveSpeed = 2.5;
    @Getter double angle = 0;

    @Getter double x;
    @Getter double y;

    @Setter boolean up    = false;
    @Setter boolean left  = false;
    @Setter boolean right = false;

    long lastShotTime = 0;

    public Player(){
        this.x = 150;
        this.y = 150;
    }

    public Player(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void moveUp(){
        x += moveSpeed * Math.sin(angle);
        y += moveSpeed * -Math.cos(angle);
    }

    @Override
    public void update() {
        if (left)  angle -= 0.1;
        if (right) angle += 0.1;
        if (up)    moveUp();
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
        // g.setClip(player);
        // this could be used to get the rotated bounds: g.getClip().getBounds2D();
        // ^ this could be useful when developing hit detection
        g.setTransform(beforeRotation);
    }

    public void shoot(ArrayList<RenderObject> gameObjects) {
        double bulletX = getX() + getHEIGHT() / 2 *  Math.sin(getAngle());
        double bulletY = getY() + getHEIGHT() / 2 * -Math.cos(getAngle());
        long currentTime = System.currentTimeMillis();
        if (lastShotTime + 100 < currentTime) {
            gameObjects.add(new Bullet(angle, bulletX, bulletY));
            lastShotTime = currentTime;
        }
    }
}
