package com.company.gameloop;

import com.company.entities.Player;
import com.company.entities.RenderObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


public class GameLoop implements Runnable{

    @Getter private final RenderLayer graphics;
    private final Updater updater;

    @Setter private boolean isRunning = false;

    @Getter private ArrayList<RenderObject> gameObjects = new ArrayList<>();

    public GameLoop(RenderLayer graphics, Updater updater){
        this.graphics = graphics;
        this.updater = updater;

        Player player = new Player();
        gameObjects.add(player);
        updater.setPlayer(player);
        graphics.addKeyBinds(player);
        graphics.setGameObjects(gameObjects);
        updater.setGameObjects(gameObjects);
    }


    public void startLoop(){
        isRunning = true;

        Thread gameLoop = new Thread(this);
        gameLoop.start();

        if (updater instanceof MPUpdater) {
            Thread updaterThread = new Thread((MPUpdater) updater);
            updaterThread.start();
        }
    }

    @Override
    public void run() {
        long lastTimeCycle = System.nanoTime();
       // long lastTimeOutput = System.currentTimeMillis();

        double unprocessedTicks = 0;
        double nsPerTick = Math.pow(10,9) / 60; // 10^9 == 1 second. 60 == the number of ticks that has to be done each second

//        int fps = 0;
//        int ticks = 0;
        // very basic game loop
        while(isRunning){
            long currentTimeCycle = System.nanoTime();
            unprocessedTicks += (currentTimeCycle - lastTimeCycle) / nsPerTick;
            lastTimeCycle = currentTimeCycle;

            while(unprocessedTicks >= 1) {
//                ticks++;
                unprocessedTicks--;
                updater.update();
            }
            graphics.repaint();

//            fps++;
//            // used just for printing out fps and ticks
//            if(System.currentTimeMillis() - lastTimeOutput > 1000){
//                lastTimeOutput += 1000;
//                System.out.println("Ticks: " + ticks + " FPS: " + fps);
//                fps = 0;
//                ticks = 0;
//            }
        }
    }
}
