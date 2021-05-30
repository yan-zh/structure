package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.BodyBuilder;

public class ThinSurface extends Actor {
    World world;
    String name;
    public float x;
    public float y;
    float width;
    float height;
    long actorId;
    Body body;


    public ThinSurface(World world, float x, float y,float width,float height,long actorId,String name){
        this.world = world;
        this.name=name;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.actorId=actorId;
        createSurface();
        ActConstants.publicInformation.put(name,this);
    }

    public void createSurface(){
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
