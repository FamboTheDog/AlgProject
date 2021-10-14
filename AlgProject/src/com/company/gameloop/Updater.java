package com.company.gameloop;

import com.company.entities.RenderObject;
import lombok.Setter;

import java.util.ArrayList;

public class Updater {

    @Setter private ArrayList<RenderObject> gameObjects;

    public void update(){
        gameObjects.forEach(RenderObject::update);
    }

}
