package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.Spine;
import com.mygdx.game.Level2.PhysicalActions.CreateSpine;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsUI;
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
    Animation frozen;

    TextureRegion currentFrame;


    float waitStateTime;
    float attackStateTime;
    float releaseStateTime;
    float statetime;


    public EatPlatform(World world, float x, float y, Animation wait, Animation attack, Animation release, Animation frozen) {//单位是m

        this.wait = wait;
        this.attack = attack;
        this.release = release;
        this.frozen = frozen;

        wait.setFrameDuration(0.3f);
        release.setFrameDuration(0.3f);
        frozen.setFrameDuration(0.3f);


        contact = false;
        state = State.wait;

        attackStateTime = 0;
        releaseStateTime = 0;
        waitStateTime = 0;
        statetime = 0;


        this.world = world;
        //
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        myFixtureDef.isSensor = false;

        number++;
        myNumber = number;


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//
        shape.setAsBox(6f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;
        myFixtureDef.isSensor = false;

        platformHeight = 1.5f;
        platformWidth = 6f;

        myBodyDef.position.set(x,y);//
        physicalX = x;
        physicalY = y;

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.eatPlatformID,"EatPlatform"+number));



        ActConstants.publicInformation.put("EatPlatform"+number,this);

        currentFrame = (TextureRegion) wait.getKeyFrames()[0];


//        //
//        PhysicalEntityDefine.defineKinematic();
//        spineBodyDef = PhysicalEntityDefine.getBd();
//        spineFixtureDef = PhysicalEntityDefine.getFd();
//
//













    }

    @Override
    public void act(float delta) {
        super.act(delta);


        if(ActConstants.isFrozen==false){
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
        }else{
            statetime += delta;
            currentFrame = (TextureRegion) frozen.getKeyFrame(statetime,true);
        }





    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-3.5f)*50f, (mySimulation.getPosition().y-3.3f)*50f);
    }


    public void addSpine(){

        CreateSpine createSpine = new CreateSpine(world,physicalX,(float)(physicalY+0.5*platformHeight),(float)(platformHeight*0.3),(float)(platformWidth*0.9),"EatPlatform"+myNumber);

        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(createSpine);
        }

        AudioManager.instance.play(AssetsUI.instance.sounds.bullet_Monster_Hit);


    }

    public void deleteSpine(){
        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(spine.getSimulation(),world);

        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(deletePhysicalEntity);
        }

    }

    public void takeSpine(Spine spine){
        this.spine = spine;
    }


    public void contact(){

        if(ActConstants.isFrozen==false){
            this.contact = true;
        }
    }
}
