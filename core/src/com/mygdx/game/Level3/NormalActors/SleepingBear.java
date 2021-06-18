package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.BodyBuilder;
import com.mygdx.game.Tools.asset.AssetsLevel2;

public class SleepingBear extends Actor {
    World world;
    String name;
//    public float x;
//    public float y;
    float width;
    float height;
    long actorId;
    public Body body;
    Animation animation;
    TextureRegion currentFrame;
    float statetime;



    //注意这个熊的刚体类型设置为了static。是否该设为kinetic?
    public SleepingBear(World world, float x, float y, float width, float height, long actorId, String name) {
        animation= AssetsLevel2.instance.pangxie.animBreath;
        this.world = world;
        this.name=name;
        setX(x);
        setY(y);
        this.width=width;
        this.height=height;
        this.actorId=actorId;
        createBear();
        ActConstants.publicInformation.put(name,this);
    }

    public void createBear(){
        Body body = BodyBuilder.createKineticBox(world, getX(), getY(), width, height,actorId, name);
        this.body=body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        body.setTransform(new Vector2(getX(),getY()),0);
//    System.out.println("getX: "+getX());
        statetime += delta;

        currentFrame = (TextureRegion)animation.getKeyFrame(statetime, true);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentFrame,(body.getPosition().x-width/2f)*50,(body.getPosition().y-height/2f)*50,width*60f,height*60f);

//        batch.draw(region,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }




}
