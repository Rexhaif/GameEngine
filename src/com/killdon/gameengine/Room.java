package com.killdon.gameengine;

import com.sun.org.apache.xml.internal.security.c14n.helper.C14nHelper;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * Created by gmfed on 13.03.2016.
 */
public class Room extends Canvas{
    private ArrayList<Object> objects;

    public int width = 640;
    public int height = 480;
    public int speed = 30;

    protected ArrayList<Integer> touchedKeys;
    protected ArrayList<Integer> pressedKeys;
    protected ArrayList<Integer> releasedKeys;

    protected ArrayList<Integer> handlerTouchedKeys;
    protected ArrayList<Integer> handlerReleasedKeys;

    public Room(int width, int height) {
        this.objects = new ArrayList<Object>();
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));

        touchedKeys = new ArrayList<Integer>();
        pressedKeys = new ArrayList<Integer>();
        releasedKeys = new ArrayList<Integer>();

        handlerTouchedKeys = new ArrayList<Integer>();
        handlerReleasedKeys = new ArrayList<Integer>();

        addKeyListener(new KeyInputHandler());
    }

    public void addObject(Object object) {
        objects.add(object);
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
        objects.forEach(com.killdon.gameengine.Object::stepProcedural);
        for (Object object : objects) {
            object.step();
        }
    }

    public void drawEvent() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); //получаем Graphics из созданной нами BufferStrategy
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
        for (Object object : objects) {
            object.draw(g);
        }
        g.dispose();
        bs.show();
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
}
