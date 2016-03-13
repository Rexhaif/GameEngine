package com.killdon.gameengine.util;

import com.killdon.gameengine.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by gmfed on 11.03.2016.
 */
public class Util {

    public static float roundMiddle(float x, float down, float up) {
        while (x >= up) x -= up - down;
        while (x < down) x += up - down;
        return x;
    }

    public static float roundIndex(float x, int index) {
        return roundMiddle(x, 0, index);
    }
}


