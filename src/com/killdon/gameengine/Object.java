package com.killdon.gameengine;

import java.awt.*;

/**
 * Created by gmfed on 11.03.2016.
 */
public abstract class Object {

    protected Sprite sprite = null;

    protected int x = 0;
    protected int y = 0;

    public Object() {}

    public Object(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public final void stepProcedural() {
        sprite.increaseIndex();
    }

    public void step() {
    }

    public void draw(Graphics g) {
        try {
            sprite.draw(g, x, y);
        } catch (NullPointerException e) {
        }
    }


}