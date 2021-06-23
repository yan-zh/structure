package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.abstraction.PhysicalAction;

public class DeletePhysicalEntity implements PhysicalAction {

    Body body;
    World world;
    Joint joint;

    boolean jointOrBody;//

    public DeletePhysicalEntity() {

    }
    public void deleteBody(Body body, World world){
        this.body = body;
        this.world = world;
        jointOrBody=false;
    }

    public void deleteJoint(Joint joint, World world){
        this.joint = joint;
        this.world = world;
        jointOrBody=true;
    }

    @Override
    public void act() {
        if(jointOrBody==true){
            world.destroyJoint(joint);
        }else{
            world.destroyBody(body);
        }

    }
}
