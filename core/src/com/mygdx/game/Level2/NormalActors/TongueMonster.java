package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class TongueMonster extends Actor {



    World world;
    Body tongueSimulation;
    FixtureDef tongueFixtureDef;
    BodyDef tongueBodyDef;
    PolygonShape tongueShape;


    float statetime;//用于替换主角动作图片的标记

    Animation top;//这个就是
    Animation tongue;

    Animation topFrozen;//这个就是
    Animation tongueFrozen;


    TextureRegion currentFrameTop;//当前该播放的图片（这个类是从texture中切一块出来）
    TextureRegion currentFrameTongue;

    float physicalX;
    float physicalY;

    Spine spine;

    static int number=0;
    int myNumber;

    float tongueHeight;
    float tongueWidth;

    boolean contact;

    public TongueMonster(World world, Animation topFrozen, Animation tongueFrozen, Animation top, Animation tongue, float physicalX, float physicalY) {
        this.world = world;
        this.top = top;
        this.tongue = tongue;
        this.tongueFrozen = tongueFrozen;
        this.topFrozen = topFrozen;
        this.physicalX = physicalX;
        this.physicalY = physicalY;

        contact = false;

        tongueHeight = 13f/ActConstants.worldSize_shapeAndPhysics;
        tongueWidth = 0.9f/ActConstants.worldSize_shapeAndPhysics;

        number++;
        myNumber = number;


        //创建主角物理模拟
        PhysicalEntityDefine.defineKinematic();
        tongueBodyDef = PhysicalEntityDefine.getBd();
        tongueFixtureDef = PhysicalEntityDefine.getFd();



        tongueShape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        tongueShape.setAsBox(tongueWidth,tongueHeight);
        tongueFixtureDef.shape = tongueShape;
        tongueFixtureDef.friction = 1;
        tongueFixtureDef.isSensor = false;

        tongueBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米


        tongueSimulation = world.createBody(tongueBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        tongueSimulation.createFixture(tongueFixtureDef).setUserData(new UserData(ActConstants.tongueMonsterID,"TongueMonster"+myNumber));

        spine = new Spine(world, physicalX, physicalY+tongueHeight, 0.5f, 3.68f);

        ActConstants.publicInformation.put("TongueMonster"+myNumber,this);



    }


    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        if(ActConstants.isFrozen==false){
            currentFrameTongue = (TextureRegion) tongue.getKeyFrame(statetime,true);
            currentFrameTop = (TextureRegion) top.getKeyFrame(statetime,true);
            if(contact==true){
                if(tongueSimulation.getPosition().y<=(physicalY+tongueHeight*2)) {
                    tongueSimulation.setLinearVelocity(0, 2);
                }else{
                    tongueSimulation.setLinearVelocity(0,0);
                }

            }else{
                if(tongueSimulation.getPosition().y>=physicalY){
                    tongueSimulation.setLinearVelocity(0,-2);
                }else{
                    tongueSimulation.setLinearVelocity(0,0);
                }
            }
        }else{
            currentFrameTongue = (TextureRegion) tongueFrozen.getKeyFrame(statetime,true);
            currentFrameTop = (TextureRegion) topFrozen.getKeyFrame(statetime,true);
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrameTongue, (tongueSimulation.getPosition().x-0.7f)*50f, (tongueSimulation.getPosition().y-0.45f)*50f);//把模拟物体的坐标拿出来，转换一下画上去

        batch.draw(currentFrameTop,(spine.getPhysicalX()-0.7f)*50f, (spine.getPhysicalY()-0.45f)*50f);
    }

    public void setContact(boolean contact){
        this.contact = contact;
    }


}
