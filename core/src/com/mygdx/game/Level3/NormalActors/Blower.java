package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class Blower extends Actor {

    public static int number;

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;


    float physicalX;
    float physicalY;

    float height;
    float width;

    Animation blowing;

    int myNumber;

    float stateTime;

    TextureRegion currentFrame;


    public Blower(World world, float physicalX, float physicalY, float width, float height, Animation blowing) {

        number++;
        myNumber = number;

        this.world = world;
        this.physicalX = physicalX;
        this.physicalY = physicalY;
        this.blowing = blowing;
        this.height = height;
        this.width = width;


        //
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//
        shape.setAsBox(width/ ActConstants.worldSize_shapeAndPhysics,height/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor = true;

        myBodyDef.position.set(physicalX,physicalY);//

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.blowerID,"Blower"+myNumber));

        ActConstants.publicInformation.put("Blower"+myNumber,this);


        stateTime=0;
        currentFrame = (TextureRegion) blowing.getKeyFrame(0);

        
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
        currentFrame = (TextureRegion) blowing.getKeyFrame(stateTime,true);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-3.7f)*50f, (mySimulation.getPosition().y-7.5f)*50f);
    }


}
