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
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.abstraction.UserData;

import java.util.HashMap;

public class Frag2 extends Actor {
    World world;

    Body eyeSimulation;
    FixtureDef eyeFixtureDef;
    BodyDef eyeBodyDef;

    Body tongueSimulation;
    FixtureDef tongueFixtureDef;
    BodyDef tongueBodyDef;
    PolygonShape tongueShape;


    Animation out;
    Animation wait;



    static short number=0;

    TextureRegion currentFrame;


    float stateTime;

    boolean tongueMark;

    float physicalX;
    float physicalY;

    short activeTime;

    boolean direction;

    public Frag2(World world, float x, float y){
        physicalX = x;
        physicalY = y;
        number++;
        direction = false;//向右

        activeTime = 0;
        //获得眼镜世界引用
        this.world = world;

        //创建眼镜物理模拟
        PhysicalEntityDefine.defineStatic();
        eyeBodyDef = PhysicalEntityDefine.getBd();
        eyeFixtureDef = PhysicalEntityDefine.getFd();


        CircleShape shape = new CircleShape();//似乎是一个像素一个
        shape.setRadius(2.5f/ActConstants.worldSize_shapeAndPhysics);
        eyeFixtureDef.shape = shape;

        eyeBodyDef.position.set(x,y);//这个表示物理世界中的米

        eyeSimulation = world.createBody(eyeBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        eyeSimulation.createFixture(eyeFixtureDef).setUserData(new UserData(ActConstants.toadEyeID,"Frag"+number));

        ActConstants.publicInformation.put("Frag"+number,this);



        //缺一个素材

        stateTime = 0;

        tongueMark = false;

       // currentFrame = (TextureRegion) wait.getKeyFrames()[0];

        //out.setFrameDuration(0.7f);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;

        if(tongueMark==true){
       //     currentFrame = (TextureRegion) out.getKeyFrame(stateTime,false);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //batch.draw(currentFrame,(eyeSimulation.getPosition().x-0.7f)*50f, (eyeSimulation.getPosition().y-0.45f)*50f);
    }

    public void active(){

        if(ActConstants.fragState==true){
            if(activeTime==0){

                tongueMark = true;


                synchronized (ActConstants.physicalActionListLock){
                    ActConstants.physicalActionList.add(new CreateTongue(direction,world,tongueSimulation,tongueFixtureDef,tongueBodyDef,tongueShape,physicalX,physicalY));
                }


            }

            activeTime++;

        }


    }

}
