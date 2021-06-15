package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.UserData;

public class laserTransmitter extends Actor {
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;



    public boolean state = true;

    public boolean triggerState = false;


    String name;

    World world;

    Timer timer;
    Timer.Task task;
    public laserTransmitter( int x, int y, long actorId, World world, String name)
    {
        //Set position
        this.setX(x);
        this.setY(y);


        //Create the physical Entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myBodyDef.type = BodyDef.BodyType.StaticBody;
        myFixtureDef = PhysicalEntityDefine.getFd();

        //这里设定盒子的大小
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);

        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        mySimulation.setGravityScale(5);
        myFixture = mySimulation.createFixture(myFixtureDef);

        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.groundID,"laserTransmitter"));


        myFixture.setUserData(new UserData(actorId, "laserTransmitter"));

        this.name = name;
        ActConstants.publicInformation.put(name, this);

        timer = new Timer();
        task = new Timer.Task() {
            @Override

            public void run() {

                laserTransmitter laserTransmitter = (laserTransmitter) ActConstants.publicInformation.get("laserTransmitter");
                laserTransmitter.emmit();
                System.out.println("sssdfsdkjfjslkfjdsl");
            }

        };
        timer.scheduleTask(task, 1, 1, 2000);// 0s之后执行，每次间隔1s，执行20次。
    }

    @Override
    public void act(float delta) {
        super.act(delta);


    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


    }

    public void emmit()
    {
        MainCharacter mainCharacter = (MainCharacter)ActConstants.publicInformation.get("MainCharacter");
        float[] direction = MyVector.getStandardVector(0,0,-1,0);
        System.out.println("Emmit");
        MyGdxGame.currentStage.addActor(new BulletSkill(AssetsLevel2.instance.decoration.jiguang, AssetsUI.instance.spriteAttack.animFhit,this.getX(),this.getY(),direction,ActConstants.laserID,1,4));
    }

    public void stop(){
        task.cancel();
        timer.clear();
    }
}
