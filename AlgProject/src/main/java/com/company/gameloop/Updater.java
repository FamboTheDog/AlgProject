package com.company.gameloop;

import com.company.entities.Player;
import com.company.entities.RenderObject;
import lombok.Setter;

import java.util.ArrayList;

abstract public class Updater {

    @Setter protected ArrayList<RenderObject> gameObjects;
    @Setter protected Player player;
    abstract void update();

}
