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

        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(6.65f/ ActConstants.worldSize_shapeAndPhysics,6.1f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米


        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.ballLauncherID,"BallLauncher"));

        ActConstants.publicInformation.put("BallLauncher",this);




    }

    @Override
    public void act(float delta) {
        super.act(delta);



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }

    public void launch(){
        ReboundBall reboundBall = new ReboundBall(world, Assets.instance.bunny.head,physicalX,physicalY+5);
        MyGdxGame.currentStage.addActor(reboundBall);
        reboundBall.impulse(1,0.1f);
    }

    public void realLaunch(){
        CreateBall createBall = new CreateBall(this);
        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(createBall);
        }

    }
}
