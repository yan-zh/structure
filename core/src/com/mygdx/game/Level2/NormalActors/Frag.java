package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.CreateTongue;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

import java.util.HashMap;

public class Frag extends Actor {
    World world;

    Body eyeSimulation;
    FixtureDef eyeFixtureDef;
    BodyDef eyeBodyDef;
    PolygonShape eyeShape;

    Body toadSimulation;
    FixtureDef toadFixtureDef;
    BodyDef toadBodyDef;
    PolygonShape toadShape;

    Body tongueSimulation;
    FixtureDef tongueFixtureDef;
    BodyDef tongueBodyDef;
    PolygonShape tongueShape;


    Animation normalEye;
    Animation normalBreath;
    Animation heatedEye;
    Animation tongueOut;

    HashMap<Integer,Animation> animationHashMap;

    static short number=0;

    TextureRegion eyeFrame;
    TextureRegion toadFrame;
    TextureRegion tongueFrame;

    float stateTime;

    boolean tongueMark;

    float physicalX;
    float physicalY;

    short activeTime;

    boolean direction;

    public Frag(World world, float x, float y){
        physicalX = x;
        physicalY = y;
        number++;
        direction = false;

        activeTime = 0;

        this.world = world;


        PhysicalEntityDefine.defineStatic();
        eyeBodyDef = PhysicalEntityDefine.getBd();
        eyeFixtureDef = PhysicalEntityDefine.getFd();


        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f/ActConstants.worldSize_shapeAndPhysics);
        eyeFixtureDef.shape = shape;

        eyeBodyDef.position.set(x,y);

        eyeSimulation = world.createBody(eyeBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        eyeSimulation.createFixture(eyeFixtureDef).setUserData(new UserData(ActConstants.toadEyeID,"Frag"+number));

        ActConstants.publicInformation.put("Frag"+number,this);


        PhysicalEntityDefine.defineStatic();
        toadBodyDef = PhysicalEntityDefine.getBd();
        toadFixtureDef = PhysicalEntityDefine.getFd();



        toadShape = new PolygonShape();
        toadShape.setAsBox(1.5f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        toadFixtureDef.shape = toadShape;

        if(direction==false){
            toadBodyDef.position.set(x+1,y-0.3f);
        }else{
            toadBodyDef.position.set(x-1,y-0.3f);
        }


        toadSimulation = world.createBody(toadBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        toadSimulation.createFixture(toadFixtureDef).setUserData(new UserData(ActConstants.groundID,"Ground"));



//
//
//        tongueShape = new PolygonShape();
//        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
//        tongueShape.setAsBox(3f/ ActConstants.worldSize_shapeAndPhysics,0.5f/ ActConstants.worldSize_shapeAndPhysics);
//        tongueFixtureDef.shape = tongueShape;
//
//        tongueFixtureDef.isSensor = true;
//
//        tongueBodyDef.position.set(physicalX-1,physicalY-1);//这个表示物理世界中的米
//
//        tongueSimulation = world.createBody(tongueBodyDef);
//        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
//        tongueSimulation.createFixture(tongueFixtureDef).setUserData(new UserData(ActConstants.groundID,"Ground"));



        normalEye = Assets.instance.mainCharacter.animBreath;
        normalBreath = Assets.instance.mainCharacter.animRun;
        heatedEye = Assets.instance.bunny.animCopterTransformBack;
        tongueOut = Assets.instance.bunny.animNormal;

        animationHashMap = new HashMap<>();
        animationHashMap.put(0,normalEye);
        animationHashMap.put(1,normalBreath);

        stateTime = 0;

        tongueMark = false;

        tongueFrame = (TextureRegion) tongueOut.getKeyFrames()[0];


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        eyeFrame = (TextureRegion) animationHashMap.get(0).getKeyFrame(stateTime,true);
        toadFrame = (TextureRegion) animationHashMap.get(1).getKeyFrame(stateTime,true);

        if(tongueMark==true){
            tongueFrame = (TextureRegion) tongueOut.getKeyFrame(stateTime,false);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(eyeFrame,(eyeSimulation.getPosition().x-0.7f)*50f, (eyeSimulation.getPosition().y-0.45f)*50f);
        batch.draw(toadFrame,(toadSimulation.getPosition().x-0.7f)*50f, (toadSimulation.getPosition().y-0.45f)*50f);

        if(tongueMark==true){
            batch.draw(tongueFrame,(physicalX-0.7f)*50f, (physicalY-0.45f)*50f);
        }
    }

    public void active(){

        if(ActConstants.fragState==true){
            if(activeTime==0){

                tongueMark = true;

                animationHashMap.replace(0,heatedEye);

                synchronized (ActConstants.physicalActionListLock){
                    ActConstants.physicalActionList.add(new CreateTongue(direction,world,tongueSimulation,tongueFixtureDef,tongueBodyDef,tongueShape,physicalX,physicalY));
                }


            }

            activeTime++;

        }


    }

}
