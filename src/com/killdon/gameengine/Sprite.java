package com.killdon.gameengine;

import com.killdon.gameengine.util.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gmfed on 11.03.2016.
 */
public class Sprite implements Runnable{

    private boolean isDownloading = false;
    private Thread threadDownload;

    public Sprite(String path) {
        this(path, 0, 0);
    }

    public Sprite(String path, int xOffset, int yOffset) {
        this.paths = new ArrayList<String>();
        this.images = new ArrayList<Image>();
        this.addPath(path);
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        new Thread(this).start();
    }

    private ArrayList<String> paths;
    private ArrayList<Image> images;
    private int count = 0;

    private int width = -1;
    private int height = -1;

    private int xOffset = 0;
    private int yOffset = 0;

    private float speed = 1f;

    private float index = 0;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void addPath(String path) {
        this.paths.add(path);
    }

    public void draw(Graphics g, int x, int y) {
        draw(g, (int) Math.floor(this.index), x, y);
    }

    public void draw(Graphics g, int index, int x,int y) {
        try {
            g.drawImage(images.get(index), x - xOffset, y - yOffset, null);
        } catch (IndexOutOfBoundsException e) {}
    }

    public void increaseIndex() {
        this.index = Util.roundIndex(this.index + this.speed, this.count);
    }

    public Image getImageFromUrl(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Toolkit.getDefaultToolkit().createImage(sourceImage.getSource());
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (count < this.paths.size()) {
                this.images.add(null);
                Download tmp = new Download();
                tmp.index = count++;
                (new Thread(tmp)).start();
            }
        }
    }

    private class Download implements Runnable{

        private int index;

        @Override
        public void run() {
            Image tmp = getImageFromUrl(paths.get(this.index));
            if (width < 0 || height < 0) {
                width = tmp.getWidth(null);
                height = tmp.getHeight(null);
            }
            images.set(this.index,tmp);
            return;
        }
    }
}
