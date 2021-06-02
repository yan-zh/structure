package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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

    Animation receive;
    Animation wait;

    float physicalX;
    float physicalY;

    TextureRegion currentFrame;

    float stateTime;

    boolean hit;




    public BallReceiver(World world, Animation receive, Animation wait, float physicalX, float physicalY) {
        this.world = world;
        this.receive = receive;
        this.wait = wait;
        this.physicalX = physicalX;
        this.physicalY = physicalY;

        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米


        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.ballReceiverID,"ballReceiver"));

        ActConstants.publicInformation.put("ballReceiver",this);

        currentFrame = (TextureRegion) wait.getKeyFrame(0);

        stateTime = 0;
        hit=false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(hit==false){
            currentFrame = (TextureRegion) wait.getKeyFrame(stateTime,true);
        }else{
            currentFrame = (TextureRegion) receive.getKeyFrame(stateTime,false);
        }
    }
}
