package com.killdon.gameengine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by gmfed on 13.03.2016.
 */
public abstract class GameNew extends JFrame implements Runnable {

    public GameNew(String title) throws HeadlessException {
        super(title);
        RoomNew room = new RoomNew(500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(room, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setVisible(true);
        start();
    }

    protected boolean running;

    protected static Room room;

    protected static ArrayList<Integer> touchedKeys;
    protected static ArrayList<Integer> pressedKeys;
    protected static ArrayList<Integer> releasedKeys;

    protected ArrayList<Integer> handlerTouchedKeys;
    protected ArrayList<Integer> handlerReleasedKeys;

    public static String NAME = "TUTORIAL 1";

    @Override
    public final void run() {
        long lastTime = System.currentTimeMillis();
        long delta;

        init();
        startEvent();
        draw();
        while(running) {
            if ((delta = System.currentTimeMillis() - lastTime) < (1000/room.speed) ) {
                continue;
            }
            lastTime = System.currentTimeMillis();
            input();
            step();
            draw();
        }
    }

    public final void start() {
        running = true;
        new Thread(this).start();
    }

    public void startEvent() {}

    protected final void init() {
        touchedKeys = new ArrayList<Integer>();
        pressedKeys = new ArrayList<Integer>();
        releasedKeys = new ArrayList<Integer>();

        handlerTouchedKeys = new ArrayList<Integer>();
        handlerReleasedKeys = new ArrayList<Integer>();

        addKeyListener(new KeyInputHandler());
    }

    protected final void input() {
        for (int i = 0; i < releasedKeys.size(); i++) {
            if (!pressedKeys.contains(releasedKeys.get(i))) {
                releasedKeys.remove(i--);
            }
        }
        for (int i = 0; i < handlerReleasedKeys.size(); i++) {
            if (pressedKeys.contains(handlerReleasedKeys.get(i)) && !releasedKeys.contains(handlerReleasedKeys.get(i))) {
                pressedKeys.remove(pressedKeys.indexOf(handlerReleasedKeys.get(i)));
                releasedKeys.add(handlerReleasedKeys.get(i));
                if (touchedKeys.contains(handlerReleasedKeys.get(i))) {
                    touchedKeys.remove(touchedKeys.indexOf(handlerReleasedKeys.get(i)));
                }
                handlerReleasedKeys.remove(i--);
            }
        }
        pressedKeys.stream().filter(touchedKeys::contains).forEach(keyCode -> {
            touchedKeys.remove(touchedKeys.indexOf(keyCode));
        });
        for (int i = 0; i < handlerTouchedKeys.size(); i++) {
            if (!touchedKeys.contains(handlerTouchedKeys.get(i)) && !pressedKeys.contains(handlerTouchedKeys.get(i))) {
                touchedKeys.add(handlerTouchedKeys.get(i));
                pressedKeys.add(handlerTouchedKeys.get(i));
                handlerTouchedKeys.remove(i--);
            }
        }
    }

    public void step() {
        room.step();
    }

    public void draw() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); //получаем Graphics из созданной нами BufferStrategy
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        room.draw(g);
        g.dispose();
        bs.show();
    }

    public static Room getRoom() {
        return room;
    }

    protected class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (!handlerTouchedKeys.contains(e.getKeyCode()) && !touchedKeys.contains(e.getKeyCode()) && !pressedKeys.contains(e.getKeyCode())) {
                handlerTouchedKeys.add(e.getKeyCode());
            }
        }

        public void keyReleased(KeyEvent e) {
            if (!handlerReleasedKeys.contains(e.getKeyCode())) {
                handlerReleasedKeys.add(e.getKeyCode());
            }
        }
    }

    public static boolean isKeyTouched(int keyCode) {
        return touchedKeys.contains(keyCode);
    }

    public static boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public static boolean isKeyReleased(int keyCode) {
        return releasedKeys.contains(keyCode);
    }

}
