package com.killdon.gameengine;

import javax.swing.*;
import java.awt.*;

/**
 * Created by gmfed on 13.03.2016.
 */
public abstract class Game extends JFrame implements Runnable {

    public Game(String title) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        start();
    }

    protected boolean running;

    protected Room room;

    public static String NAME = "TUTORIAL 1";

    @Override
    public final void run() {
        long lastTime = System.currentTimeMillis();
        long delta;

        init();
        startEvent();
        drawEvent();
        while(running) {
            if ((delta = System.currentTimeMillis() - lastTime) < (1000/room.speed) ) {
                continue;
            }
            lastTime = System.currentTimeMillis();
            input();
            step();
            drawEvent();
        }
    }

    public final void start() {
        running = true;
        new Thread(this).start();
    }

    public void startEvent() {}

    protected final void init() {
    }

    public void input() {
        room.input();
    }

    public void step() {
        room.step();
    }

    public void drawEvent() {
        room.drawEvent();
    }

    public Room getRoom() {
        return room;
    }

    public final void setRoom(Room room) {
        try {
            remove(room);
        } catch (NullPointerException e) {}
        this.room = room;
        add(room, BorderLayout.CENTER);
        pack();
    }
}
