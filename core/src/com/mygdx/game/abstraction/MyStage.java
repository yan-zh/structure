package com.mygdx.game.abstraction;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MyStage extends Stage {

    public World world;
    public float cUp;
    public float cDown;
    public float cleft;
    public float cright;

    public World getWorld(){
        return this.world;
    }
}
