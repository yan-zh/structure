package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.abstraction.PhysicalAction;

public class DeleteBubble implements PhysicalAction {
    Body body;
    World world;
    float posX;
    float posY;

    public DeleteBubble() {

    }
    public void deleteBody(Body body, float posX, float posY){
        this.body = body;
        this.posX=posX;
        this.posY=posY;

    }



    @Override
    public void act() {

        System.out.println("eee");
        body.setTransform(posX,posY,0);


    }
}
