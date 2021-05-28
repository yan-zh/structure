package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.abstraction.PhysicalAction;

public class DelateBody implements PhysicalAction {

    Body body;
    World world;

    public DelateBody(Body body, World world) {
        this.body = body;
        this.world = world;
    }

    @Override
    public void act() {
        world.destroyBody(body);
    }
}
