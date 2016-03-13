package com.killdon.gameengine;

import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by gmfed on 13.03.2016.
 */
public class RoomNew extends Canvas{
    private ArrayList<Object> objects;

    public int width = 640;
    public int height = 480;
    public int speed = 30;

    public RoomNew(int width, int height) {
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
