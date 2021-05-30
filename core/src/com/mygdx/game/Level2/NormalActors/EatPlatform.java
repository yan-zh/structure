package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.CreateSpine;
import com.mygdx.game.Level2.PhysicalActions.CreateTongue;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class EatPlatform extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;

    Spine spine;

    Body spineSimulation;
    FixtureDef spineFixtureDef;
    BodyDef spineBodyDef;
    PolygonShape spineShape;

    float platformWidth;
    float platformHeight;

    float physicalX;
    float physicalY;

    static int number=0;

    int myNumber;


    public EatPlatform(World world, float x, float y) {//单位是m
        this.world = world;
        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        myFixtureDef.isSensor = false;

        number++;
        myNumber = number;


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(3f/ ActConstants.worldSize_shapeAndPhysics,1f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;
        platformHeight = 1;
        platformWidth = 3;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米
        physicalX = x;
        physicalY = y;

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.eatPlatformID,"EatPlatform"+number));
        System.out.println("EatPlatform"+number);


        ActConstants.publicInformation.put("EatPlatform"+number,this);



//        //创建主角物理模拟
//        PhysicalEntityDefine.defineKinematic();
//        spineBodyDef = PhysicalEntityDefine.getBd();
//        spineFixtureDef = PhysicalEntityDefine.getFd();
//
//
//
//        spineShape = new PolygonShape();
//        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
//        spineShape.setAsBox((float)(platformHeight*0.9),(float)(platformHeight*0.3));
//        spineFixtureDef.shape = spineShape;
//
//        spineBodyDef.position.set(x,y);//这个表示物理世界中的米
//
//        spineSimulation = world.createBody(spineBodyDef);
//        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
//        spineSimulation.createFixture(spineFixtureDef).setUserData(new UserData(ActConstants.spineID,"Spine"+number));


       // spine.setDraw(false);












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


    public void addSpine(){

        CreateSpine createSpine = new CreateSpine(world,physicalX,(float)(physicalY+0.5*platformHeight),(float)(platformHeight*0.3),(float)(platformWidth*0.9),"EatPlatform"+myNumber);

        ActConstants.physicalActionList.add(createSpine);

    }

    public void deleteSpine(){
        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(spine.getSimulation(),world);
        ActConstants.physicalActionList.add(deletePhysicalEntity);
    }

    public void takeSpine(Spine spine){
        this.spine = spine;
    }


    public void contact(){

    }
}
