package com.killdon.gameengine;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by gmfed on 13.03.2016.
 */
public abstract class Room {

    private ArrayList<Instance> instances;

    private Game game;
    private int width = 640;
    private int height = 480;
    private int speed = 30;

    private Color backgroundColor = Color.lightGray;

    public Room(Game game) {
        this(game,0,0,null);
    }

    public Room(Game game, int width, int height) {
        this(game, width, height, null);
    }

    public Room(Game game, int width, int height, ArrayList<Instance> instances) {
        if (instances != null) {
            this.instances = instances;
        } else {
            this.instances = new ArrayList<Instance>();
        }
        this.game = game;
        this.width = width;
        this.height = height;
        init();
    }

    public final int getWidth() {
        return width;
    }

    public final void setWidth(int width) {
        this.width = width;
    }

    public final int getHeight() {
        return height;
    }

    public final void setHeight(int height) {
        this.height = height;
    }

    public final int getSpeed() {
        return speed;
    }

    public final void setSpeed(int speed) {
        this.speed = speed;
    }

    public final void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Game getGame() {
        return game;
    }

    public void init() {}

    public final void addInstance(Instance instance) {
        instances.add(instance);
    }

    public final ArrayList<Instance> getInstances() {
        return instances;
    }

    public final void setInstances(ArrayList<Instance> instances) {
        this.instances = instances;
    }

    public final void stepEvent() {
        instances.forEach(Instance::stepProcedural);
        instances.forEach(Instance::stepEvent);
    }

    public final Color getBackgroundColor() {
        return backgroundColor;
    }

    public final void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
