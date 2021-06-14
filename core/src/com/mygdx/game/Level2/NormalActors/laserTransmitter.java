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
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.UserData;

public class laserTransmitter extends Actor {
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;


    Animation wait;
    Animation trigger;

    TextureRegion currentFrame;

    public boolean state = true;

    public boolean triggerState = false;

    float statetime;

    String name;

    World world;
    public laserTransmitter(Animation animationWait, Animation animationTrigger, int x, int y, long actorId, World world, String name)
    {
        //Set position
        this.setX(x);
        this.setY(y);

        //Set the animation of the wait state and absorb
        this.wait = animationWait;
        this.trigger = animationTrigger;

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

        Timer timer = new Timer();
        Timer.Task timerTask = new Timer.Task() {
            @Override

            public void run() {

                laserTransmitter laserTransmitter = (laserTransmitter) ActConstants.publicInformation.get("laserTransmitter");
                laserTransmitter.emmit();
                System.out.println("sssdfsdkjfjslkfjdsl");
            }

        };
        timer.scheduleTask(timerTask, 1, 8, 2000);// 0s之后执行，每次间隔1s，执行20次。
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        if(state == true) currentFrame = (TextureRegion)wait.getKeyFrame(statetime, true);

        else currentFrame = (TextureRegion) trigger.getKeyFrame(statetime, true);




    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame, (mySimulation.getPosition().x - 0.7f)* 50f, (mySimulation.getPosition().y - 0.45f)*50f);

    }

    public void emmit()
    {
        MainCharacter mainCharacter = (MainCharacter)ActConstants.publicInformation.get("MainCharacter");
        float[] direction = MyVector.getStandardVector(0,0,-1,0);
        System.out.println("Emmit");
        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animRun,this.getX(),this.getY(),direction,ActConstants.laserID,1));
    }
}
