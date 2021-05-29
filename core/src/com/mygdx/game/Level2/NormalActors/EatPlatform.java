package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class EatPlatform extends Actor {
    Spine spine;
    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;

    float pWidth;
    float pHeight;

    public EatPlatform(World world, float x, float y) {//单位是m
        this.world = world;
        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        myFixtureDef.isSensor = false;



        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(3f/ ActConstants.worldSize_shapeAndPhysics,1f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;
        pHeight = 1;
        pWidth = 3;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.eatPlatformID,"EatPlatformID"));


        ActConstants.publicInformation.put("EatPlatformID",this);

        this.spine = new Spine(world,x,(float)(y-0.3*pHeight),(float)(pHeight*0.5),pWidth);



    }

    @Override
    public void act(float delta) {
        super.act(delta);

        //三个状态，等待，攻击，返回



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
