package org.example.game;

import java.util.ArrayList;
import java.awt.Point;

public class Snake {
    public ArrayList<Point> body = new ArrayList<Point>();
    Direction direction;
    boolean alive = true;

    Snake(ArrayList<Point> body, Direction direction){
        this.body = body;
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public void kill(){
        this.alive = false;
    }
}
