package com.killdon.gameengine;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Created by gmfed on 14.03.2016.
 */
public class Window extends Canvas {

    private Room room;
    private Color background = Color.black;

    public Window() {
        this(null);
    }

    public Window(GraphicsConfiguration config) {
        super(config);
    }

    public void drawEvent() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2); //создаем BufferStrategy для нашего холста
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics(); //получаем Graphics из созданной нами BufferStrategy
        drawBackground(g);
        g.setColor(room.getBackgroundColor());
        g.fillRect(0, 0, room.getWidth(), room.getHeight());
        for (Instance instance : room.getInstances()) {
            instance.drawEvent(g);
        }
        g.dispose();
        bs.show();
    }

    public void drawBackground(Graphics g) {
        g.setColor(background);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setRoom(Room room) {
        this.room = room;
        setPreferredSize(new Dimension(room.getWidth(), room.getHeight()));
    }
}
