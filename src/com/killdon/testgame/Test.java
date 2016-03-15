package com.killdon.testgame;

import com.killdon.gameengine.Room;
import com.killdon.gameengine.Sprite;
import com.killdon.testgame.instances.InstanceHero;
import com.killdon.testgame.rooms.RoomStart;

import java.awt.*;

/**
 * Created by gmfed on 13.03.2016.
 */
public class Test extends com.killdon.gameengine.Game {

    public Test(String title) throws HeadlessException {
        super(title);
    }

    @Override
    public void init() {
        setRoom(new RoomStart(this));
    }
}
