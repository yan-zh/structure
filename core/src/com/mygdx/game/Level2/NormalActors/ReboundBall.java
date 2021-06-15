package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class ReboundBall extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    CircleShape shape;

    TextureRegion ball;
    ParticleEffect effect;

    float physicalX;
    float physicalY;

    TextureRegion currentFrame;

    float stateTime;

    float effectStateTime;

    public ReboundBall(World world, TextureRegion ball, float physicalX, float physicalY) {
        this.world = world;
        this.ball = ball;
        this.physicalX = physicalX;
        this.physicalY = physicalY;

        //创建主角物理模拟
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        shape = new CircleShape();
        shape.setRadius(0.5f/ActConstants.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米

        myFixtureDef.shape = shape;
        myFixtureDef.restitution = 0.7f;
        myFixtureDef.friction = 0;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米
        myBodyDef.fixedRotation = true;


        mySimulation = world.createBody(myBodyDef);
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.reboundBallID,"ReboundBall"));
        mySimulation.setGravityScale(1);


        ActConstants.publicInformation.put("ReboundBall",this);

        currentFrame = ball;

        stateTime = 0;
        effectStateTime = 0;

        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("core/assets/particle3.p"),Gdx.files.internal("core/assets/"));

        effect.setPosition(physicalX*ActConstants.worldSize_pAndPhysic,physicalY*ActConstants.worldSize_pAndPhysic);



    }




    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;


        physicalX = mySimulation.getPosition().x;
        physicalY = mySimulation.getPosition().y;


        effect.setPosition(physicalX*ActConstants.worldSize_pAndPhysic,physicalY*ActConstants.worldSize_pAndPhysic);





    }



    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


//        batch.draw(currentFrame, (mySimulation.getPosition().x - 0.7f)* 50f, (mySimulation.getPosition().y - 0.45f)*50f,(float)0.5*currentFrame.getRegionWidth(),(float)0.5*currentFrame.getRegionHeight(), currentFrame.getRegionWidth(), currentFrame.getRegionHeight(),(float) 1.0,(float) 1.0, (float) (180*mySimulation.getAngle()/3.14),true);
        effect.draw(batch,Gdx.graphics.getDeltaTime());

    }

    public void impulse(float xIn, float yIn){
        mySimulation.applyLinearImpulse(mySimulation.getPosition().x,mySimulation.getPosition().y,xIn,yIn,true);
    }

    @Override
    public boolean remove() {

        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(mySimulation,world);

        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(deletePhysicalEntity);
        }

        synchronized (ActConstants.publicInformationLock){
            ActConstants.publicInformation.remove("ReboundBall");
        }

        return super.remove();
    }
}
