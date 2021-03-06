package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.CreateBall;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class BallLauncher extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;

    Animation push;
    Animation wait;

    float physicalX;
    float physicalY;

    TextureRegion currentFrame;

    public boolean launch;

    float stateTimeWait;
    float stateTimeLaunch;

    public BallLauncher(World world, Animation push, Animation wait, float physicalX, float physicalY) {
        this.world = world;
        this.push = push;
        this.wait = wait;
        this.physicalX = physicalX;
        this.physicalY = physicalY;
        this.launch = false;

        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米


        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.ballLauncherID,"BallLauncher"));

        ActConstants.publicInformation.put("BallLauncher",this);

        currentFrame = (TextureRegion) wait.getKeyFrame(0);

        stateTimeLaunch = 0;
        stateTimeWait = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);


        if(launch==false){
            stateTimeWait += delta;
            currentFrame = (TextureRegion) wait.getKeyFrame(stateTimeLaunch,true);
        }else{
            stateTimeLaunch += delta;
            currentFrame = (TextureRegion) push.getKeyFrame(stateTimeLaunch,false);
            if(push.isAnimationFinished(stateTimeLaunch)){
                realLaunch();
                launch=false;
            }
        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }

    public void launch(){
        ReboundBall reboundBall = new ReboundBall(world, Assets.instance.bunny.animCopterTransformBack,physicalX,physicalY+2);
        MyGdxGame.currentStage.addActor(reboundBall);
        reboundBall.impulse(100,100);
    }

    public void realLaunch(){
        CreateBall createBall = new CreateBall(this);
        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(createBall);
        }

    }
}
