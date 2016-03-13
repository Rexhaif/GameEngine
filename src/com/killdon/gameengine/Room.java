package com.killdon.gameengine;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by gmfed on 12.03.2016.
 */
public class Room {

    private ArrayList<Object> objects;

    public int width = 640;
    public int height = 480;
    public int speed = 30;

    public Room(int width, int height) {
        this.objects = new ArrayList<Object>();
        this.width = width;
        this.height = height;
    }

    public void addObject(Object object) {
        objects.add(object);
    }

    public void input() {

    }

    public void step() {
        objects.forEach(com.killdon.gameengine.Object::stepProcedural);
        for (Object object : objects) {
            object.step();
        }
    }

    public void draw(Graphics g) {
        for (Object object : objects) {
            object.draw(g);
        }
    }


}
