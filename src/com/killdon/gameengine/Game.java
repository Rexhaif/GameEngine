package com.killdon.gameengine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by gmfed on 13.03.2016.
 */
public abstract class Game extends JFrame implements Runnable {

    protected ArrayList<Integer> touchedKeys;
    protected ArrayList<Integer> pressedKeys;
    protected ArrayList<Integer> releasedKeys;

    protected ArrayList<Integer> handlerTouchedKeys;
    protected ArrayList<Integer> handlerReleasedKeys;

    public Game(String title) {
        super(title);

        touchedKeys = new ArrayList<Integer>();
        pressedKeys = new ArrayList<Integer>();
        releasedKeys = new ArrayList<Integer>();

        handlerTouchedKeys = new ArrayList<Integer>();
        handlerReleasedKeys = new ArrayList<Integer>();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        window = new Window();
        window.addKeyListener(new KeyInputHandler());
        add(window, BorderLayout.CENTER);
        setResizable(false);
        setVisible(true);
        start();
    }

    protected boolean running;

    protected Room room;
    protected Window window;

    public boolean fullScreen = false;
    public boolean frame = true;

    public static String NAME = "TUTORIAL 1";

    @Override
    public final void run() {
        long lastTime = System.currentTimeMillis();
        long delta;

        init();
        startEvent();
        drawEvent();
        while(running) {
            if ((delta = System.currentTimeMillis() - lastTime) < (1000/room.getSpeed()) ) {
                continue;
            }
            lastTime = System.currentTimeMillis();
            inputProcedural();
            stepEvent();
            drawEvent();
        }
    }

    public final void start() {
        running = true;
        new Thread(this).start();
    }

    public void init() {
    }

    protected final void startEvent() {
        room.getInstances().forEach(Instance::startEvent);
    }


    protected final void inputProcedural() {
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

    public void stepEvent() {
        room.stepEvent();
    }

    public void drawEvent() {
        window.drawEvent();
    }

    public boolean isKeyTouched(int keyCode) {
        return touchedKeys.contains(keyCode);
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public boolean isKeyReleased(int keyCode) {
        return releasedKeys.contains(keyCode);
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

    public Room getRoom() {
        return room;
    }

    public final void setRoom(Room room) {
        this.room = room;
        window.setRoom(this.room);
        startEvent();
        pack();
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        if (this.fullScreen != fullScreen) {
            this.fullScreen = fullScreen;
            if (this.fullScreen) {
                dispose();
                setUndecorated(true);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                setVisible(true);
                toFront();
                window.requestFocus();
            } else {
                dispose();
                setUndecorated(!frame);
                pack();
                setLocationRelativeTo(null);
                setVisible(true);
                toFront();
                window.requestFocus();
            }
        }
    }

    public boolean isFrame() {
        return frame;
    }

    public void setFrame(boolean frame) {
        if (this.frame != frame) {
            this.frame = frame;
            if (!fullScreen) {
                dispose();
                setUndecorated(!this.frame);
                pack();
                setVisible(true);
                toFront();
                window.requestFocus();
            }
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void exit(int status) {
        System.exit(status);
    }
}
