package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.EatPlatform;
import com.mygdx.game.Level2.NormalActors.Spine;
import com.mygdx.game.abstraction.PhysicalAction;

public class CreateSpine implements PhysicalAction {

    World world;
    float x;
    float y;
    float height;
    float width;

    Spine spine;

    String informaitonName;

    public CreateSpine(World world, float x, float y, float height, float width,String informationName) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.informaitonName = informationName;
    }

    @Override
    public void act() {
        this.spine = new Spine(world,x,y,height,width);
        ((EatPlatform)ActConstants.publicInformation.get(informaitonName)).takeSpine(spine);
    }

}
