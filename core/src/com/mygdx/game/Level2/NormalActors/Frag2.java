package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.CreateTongue;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel0;
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

    static short number=0;

    TextureRegion currentFrame;


    float stateTime;

    boolean tongueMark;

    float physicalX;
    float physicalY;

    short activeTime;

    boolean direction;

    TextureRegion[] textureRegions;

    int count;

    public Frag2(World world, float x, float y){
        physicalX = x;
        physicalY = y;
        number++;
        direction = false;//向右

        activeTime = 0;
        //获得眼镜世界引用
        this.world = world;
        count=0;

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

        textureRegions = new TextureRegion[3];
        textureRegions[0] = AssetsLevel0.instance.qingwa.qingwa;
        textureRegions[1] = AssetsLevel0.instance.qingwa.qingwa1;
        textureRegions[2] = AssetsLevel0.instance.qingwa.qingwa2;

        out = new Animation(0.25f,textureRegions);
        currentFrame = AssetsLevel0.instance.qingwa.qingwa;


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;



        if(tongueMark==true){
            if(count==0){
                stateTime = 0;
                count=1;
            }

            currentFrame = (TextureRegion) out.getKeyFrame(stateTime,false);

        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(eyeSimulation.getPosition().x-3.25f)*50f, (eyeSimulation.getPosition().y-1.5f)*50f,500/2,300/2);

    }

    public void active(){

        if(ActConstants.fragState==true){
            if(activeTime==0){

                tongueMark = true;


                synchronized (ActConstants.physicalActionListLock){
                    ActConstants.physicalActionList.add(new CreateTongue(direction,world,tongueSimulation,tongueFixtureDef,tongueBodyDef,tongueShape,physicalX-0.75f,physicalY+1.2f));
                }

                AudioManager.instance.play(AssetsLevel0.instance.sounds.frog_Triggered);
            }

            activeTime++;


        }



    }

}
