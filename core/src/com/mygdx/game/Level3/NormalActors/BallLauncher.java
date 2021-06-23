package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level3.Physicalactions.CreateBall;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.UserData;

public class BallLauncher extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;

    TextureRegion currentFrame;

    float physicalX;
    float physicalY;


    public boolean launch;




    public BallLauncher(World world,TextureRegion currentFrame, float physicalX, float physicalY) {
        this.world = world;

        this.physicalX = physicalX;
        this.physicalY = physicalY;
        this.launch = false;
        this.currentFrame = currentFrame;

        //
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//
        shape.setAsBox(6.65f/ ActConstants.worldSize_shapeAndPhysics,6.1f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(physicalX,physicalY);//


        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.ballLauncherID,"BallLauncher"));

        ActConstants.publicInformation.put("BallLauncher",this);




    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if(launch==true){

            realLaunch();
            launch=false;

        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-5.75f)*50f, (mySimulation.getPosition().y-4f)*50f,575f,375f);
    }

    public void launch(){
        ReboundBall reboundBall = new ReboundBall(world, Assets.instance.bunny.head,physicalX,physicalY+5);
        MyGdxGame.currentStage.addActor(reboundBall);
        reboundBall.impulse(1,0.1f);
        AudioManager.instance.play(AssetsUI.instance.sounds.bullet_Monster_Hit);
    }

    public void realLaunch(){
        CreateBall createBall = new CreateBall(this);
        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(createBall);
        }


    }
}
