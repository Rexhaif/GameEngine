package com.killdon.testgame.instances;


import com.killdon.gameengine.*;
import com.killdon.testgame.sprites.SpriteHero;

import java.awt.event.KeyEvent;

/**
 * Created by gmfed on 12.03.2016.
 */
public class InstanceHero extends Instance {

    public InstanceHero(Game game) {
        super(game);
    }

    private int speed = 4;

    @Override
    public void startEvent() {
        setSprite(new SpriteHero());
        x = game.getRoom().getWidth()/2;
        y = game.getRoom().getHeight()/2;
    }

    @Override
    public void stepEvent() {
        if (game.isKeyPressed(KeyEvent.VK_D)) {
            x = Math.min(x + speed, game.getRoom().getWidth() - sprite.getWidth()/2);
        }
        if (game.isKeyPressed(KeyEvent.VK_A)) {
            x = Math.max(x - speed, sprite.getWidth() / 2);
        }
        if (game.isKeyPressed(KeyEvent.VK_S)) {
            y = Math.min(y + speed, game.getRoom().getHeight() - sprite.getHeight() / 2);
        }
        if (game.isKeyPressed(KeyEvent.VK_W)) {
            y = Math.max(y - speed, sprite.getHeight() / 2);
        }
        if (game.isKeyTouched(KeyEvent.VK_UP)) {
            speed = Math.max(1,Math.min(16,speed + 1));
        }
        if (game.isKeyTouched(KeyEvent.VK_DOWN)) {
            speed = Math.max(1,Math.min(16,speed - 1));
        }
        if (game.isKeyTouched(KeyEvent.VK_F4)) {
            game.setFullScreen(!game.isFullScreen());
        }
        if (game.isKeyTouched(KeyEvent.VK_F)) {
            game.setFrame(!game.isFrame());
        }
        if (game.isKeyTouched(KeyEvent.VK_ESCAPE)) {
            game.exit();
        }
    }
}
