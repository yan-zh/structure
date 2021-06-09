package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.BodyBuilder;

public class PswDoor extends Actor {
    World world;
    String name;
    float width;
    float height;
    long actorId;
    public Body body;
    boolean moved;

    public PswDoor(World world, float x, float y, float width, float height, long actorId, String name){
        this.world = world;
        this.name=name;
        setX(x);
        setY(y);
        this.width=width;
        this.height=height;
        this.actorId=actorId;
        this.moved=false;
        createDoor();
        ActConstants.publicInformation.put(name,this);

    }

    private void createDoor(){
        Body body = BodyBuilder.createKineticBox(world, getX(), getY(), width, height,actorId, name);
        this.body=body;
    }

    public void move(){
        if(moved==false){
            MoveByAction action= Actions.moveBy(-width,0,1f);
            this.addAction(action);
            moved=true;
        }


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setTransform(new Vector2(getX(),getY()),0);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        batch.draw(region,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }


}
