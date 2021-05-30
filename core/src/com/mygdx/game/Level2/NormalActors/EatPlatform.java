package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    boolean contact;
    enum State
    {
        wait,release;
    }
    State state;

    Animation wait;
    Animation attack;
    Animation release;

    TextureRegion currentFrame;


    float waitStateTime;
    float attackStateTime;
    float releaseStateTime;


    public EatPlatform(World world, float x, float y, Animation wait, Animation attack, Animation release) {//单位是m

        this.wait = wait;
        this.attack = attack;
        this.release = release;


        contact = false;
        state = State.wait;

        attackStateTime = 0;
        releaseStateTime = 0;
        waitStateTime = 0;


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

        currentFrame = (TextureRegion) wait.getKeyFrames()[0];


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


        if(contact==true){
            if(state==State.wait){
                attackStateTime += delta;
                currentFrame = (TextureRegion) attack.getKeyFrame(attackStateTime,false);
                if(attack.isAnimationFinished(attackStateTime)){
                    this.addSpine();
                    attackStateTime=0;
                    state = State.release;
                }
            }else if(state==State.release){
                releaseStateTime += delta;
                currentFrame = (TextureRegion) release.getKeyFrame(releaseStateTime,false);
                if(release.isAnimationFinished(releaseStateTime)){
                    releaseStateTime = 0;
                    this.deleteSpine();
                    state = State.wait;
                    contact = false;
                }
            }
        }else{
            waitStateTime += delta;
            currentFrame = (TextureRegion) wait.getKeyFrame(waitStateTime,true);
        }




    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
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
        this.contact = true;
    }
}
