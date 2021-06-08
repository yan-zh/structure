package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class ReverberateAxe extends Actor {
    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;


    Body mySimulation2;
    FixtureDef myFixtureDef2;
    BodyDef myBodyDef2;
    PolygonShape shape2;


    Body sensorSimulation;
    FixtureDef sensorFixtureDef;
    BodyDef sensorBodyDef;
    PolygonShape sensorshape;


    float statetime;//用于替换主角动作图片的标记

    TextureRegion picutre;//这个就是

    float physicalX;
    float physicalY;

    float physicalYStart;


    public boolean move;

    float physicalWidth;
    float physicalHeight;

    boolean down;

    public ReverberateAxe(World world, float x, float y) {

        down=true;
        move=false;
        physicalYStart = y;
        //获得物理世界引用
        this.world = world;

        physicalWidth = 4f;
        physicalHeight = 3.5f;

        //创建主角物理模拟
        PhysicalEntityDefine.defineKinematic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();
        myFixtureDef.isSensor = true;
        myBodyDef.gravityScale = 0;


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox((float)(physicalWidth*0.9) / ActConstants.worldSize_shapeAndPhysics, (physicalHeight/10) / ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;



        myBodyDef.position.set(x, y);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.axeID, "Axe"));



        PhysicalEntityDefine.defineKinematic();
        myBodyDef2 = PhysicalEntityDefine.getBd();
        myFixtureDef2 = PhysicalEntityDefine.getFd();
        myFixtureDef2.isSensor = false;
        myBodyDef2.gravityScale = 0;


        shape2 = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape2.setAsBox(physicalWidth / ActConstants.worldSize_shapeAndPhysics, (physicalHeight/2) / ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef2.shape = shape2;


        myBodyDef2.position.set(x, y+1);//这个表示物理世界中的米

        mySimulation2 = world.createBody(myBodyDef2);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation2.createFixture(myFixtureDef2).setUserData(new UserData(ActConstants.groundID, "Ground"));




        //创建sensor
        PhysicalEntityDefine.defineStatic();
        sensorBodyDef = PhysicalEntityDefine.getBd();
        sensorFixtureDef = PhysicalEntityDefine.getFd();

        sensorFixtureDef.isSensor = true;


        sensorshape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        sensorshape.setAsBox(1f / ActConstants.worldSize_shapeAndPhysics, 8f / ActConstants.worldSize_shapeAndPhysics);
        sensorFixtureDef.shape = sensorshape;

        sensorBodyDef.position.set(x - 12, y - 8f);//这个表示物理世界中的米

        sensorSimulation = world.createBody(sensorBodyDef);

        sensorSimulation.createFixture(sensorFixtureDef).setUserData(new UserData(ActConstants.axeSensorID, "Axe"));


        ActConstants.publicInformation.put("Axe", this);


        this.picutre = (TextureRegion) Assets.instance.bunny.animNormal.getKeyFrames()[0];

    }


    @Override
    public void act(float delta) {
        super.act(delta);
        if(move==true){
            if(mySimulation.getPosition().y>=physicalYStart){
                down=true;
            }else if(mySimulation.getPosition().y<=physicalYStart-11.4){
                down=false;
            }

            if(down==true){
                mySimulation.setLinearVelocity(0,-8);
                mySimulation2.setLinearVelocity(0,-8);

            }else{
                mySimulation.setLinearVelocity(0,3);
                mySimulation2.setLinearVelocity(0,3);
            }

        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(picutre,(mySimulation.getPosition().x-(float)(0.5*physicalWidth))*50f, (mySimulation.getPosition().y-(float)(0.5*physicalHeight))*50f);
    }
}
