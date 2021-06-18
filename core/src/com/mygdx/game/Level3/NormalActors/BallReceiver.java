package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class BallReceiver extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;

    TextureRegion currentFrame;

    float physicalX;
    float physicalY;


    float stateTime;

    public boolean hit;

    ParticleEffect effect;
    float effectTime;

    public boolean fire;




    public BallReceiver(World world, TextureRegion currentFrame, float physicalX, float physicalY) {
        this.world = world;
        this.currentFrame = currentFrame;
        this.physicalX = physicalX;
        this.physicalY = physicalY;

        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(7.8f/ ActConstants.worldSize_shapeAndPhysics,0.7f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米


        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.ballReceiverID,"ballReceiver"));

        ActConstants.publicInformation.put("ballReceiver",this);


        stateTime = 0;
        hit=false;

        effectTime = 0;

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("core/assets/particle3.p"),Gdx.files.internal("core/assets/"));

        effect.setPosition(physicalX*ActConstants.worldSize_pAndPhysic,physicalY*ActConstants.worldSize_pAndPhysic);



        fire=false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        effectTime += delta;
        effect.setPosition(physicalX*ActConstants.worldSize_pAndPhysic,physicalY*ActConstants.worldSize_pAndPhysic);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-5.5f)*50f, (mySimulation.getPosition().y-2.8f)*50f);

        if(fire ==true){
            effect.draw(batch,Gdx.graphics.getDeltaTime());
        }

    }
}
