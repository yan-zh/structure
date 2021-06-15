package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.abstraction.DieAction;
import com.mygdx.game.abstraction.UserData;

public class Flower extends Actor {
    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;

    Animation rest;
    Animation contact;
    Animation disappear;

    TextureRegion currentFrame;

    float stateTime;

    float physicalX;
    float physicalY;

    boolean isContact;
    boolean isRest;
    boolean isDelete;

    int count;

    public Flower(World world, float x, float y,Animation rest, Animation contact, Animation disappear) {
        this.world = world;
        this.rest = rest;
        this.contact = contact;
        this.disappear = disappear;
        this.physicalX = x;
        this.physicalY = y;


        isContact = false;
        isRest = true;
        isDelete = false;
        count=0;

        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(6f/ ActConstants.worldSize_shapeAndPhysics,9f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor = false;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.flowerID,"Flower"));

        ActConstants.publicInformation.put("Flower",this);


        currentFrame = (TextureRegion) rest.getKeyFrames()[0];



    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        if(isRest==true){
            currentFrame = (TextureRegion) rest.getKeyFrame(stateTime,true);
        }else if(isContact==true){
            currentFrame = (TextureRegion) contact.getKeyFrame(stateTime,false);
            if(contact.isAnimationFinished(stateTime)){


                if(count==0){
                    this.deleteBody();
                    MyGdxGame.currentStage.addActor(new DieAction(AssetsLevel0.instance.srh.animDead,(mySimulation.getPosition().x-5f)*50f, (mySimulation.getPosition().y-4f)*50f));
                    isDelete = true;

                    Timer timer = new Timer();

                    Timer.Task timerTask = new Timer.Task() {
                        @Override
                        public void run() {
                            Flower flower = (Flower) ActConstants.publicInformation.get("Flower");
                            flower.remove();
                            ActConstants.publicInformation.remove("Flower");

                        }
                    };

                    timer.scheduleTask(timerTask, 0.5f, 0, 0);// 0s之后执行，每次间隔1s，再次重复执行20次。
                }

                count=1;

            }
        }




    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(isDelete==false){
            batch.draw(currentFrame,(mySimulation.getPosition().x-5f)*50f, (mySimulation.getPosition().y-4f)*50f);
        }
    }


    public void deleteBody() {

        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(mySimulation,world);
        ActConstants.physicalActionList.add(deletePhysicalEntity);

    }

    public void contact(){
        isContact = true;
        isRest = false;

        AudioManager.instance.play(AssetsLevel0.instance.sounds.follower_Accept);
    }

}
