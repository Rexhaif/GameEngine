package com.killdon.testgame.sprites;

import com.killdon.gameengine.Sprite;

/**
 * Created by gmfed on 15.03.2016.
 */
public class SpriteHero extends Sprite{

    public SpriteHero() {
        super();
    }

    @Override
    public void init() {
        addPath("man.png");
        setOffset(30, 46);
    }
}
