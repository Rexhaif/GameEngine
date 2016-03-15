package com.killdon.testgame.rooms;

import com.killdon.gameengine.Game;
import com.killdon.gameengine.Instance;
import com.killdon.gameengine.Room;
import com.killdon.testgame.instances.InstanceHero;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by gmfed on 15.03.2016.
 */
public class RoomStart extends Room {

    public RoomStart(Game game) {
        super(game);
    }

    @Override
    public void init() {
        setSize(500, 500);
        addInstance(new InstanceHero(this.getGame()));
    }
}
