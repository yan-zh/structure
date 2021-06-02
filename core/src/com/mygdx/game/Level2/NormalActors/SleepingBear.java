package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.BodyBuilder;

public class SleepingBear extends Actor {
    World world;
    String name;
    public float x;
    public float y;
    float width;
    float height;
    long actorId;
    Body body;


//注意这个熊的刚体类型设置为了static。是否该设为kinetic?
    public SleepingBear(World world, float x, float y, float width, float height, long actorId, String name) {
        this.world = world;
        this.name=name;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.actorId=actorId;
        createBear();
        ActConstants.publicInformation.put(name,this);
    }

    public void createBear(){
        Body body = BodyBuilder.createBox(world, x, y, width, height, false, actorId, name);
        this.body=body;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        batch.draw(region,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }




}
