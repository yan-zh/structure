package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;
import org.graalvm.compiler.virtual.phases.ea.EffectList;

public class ReboundBall extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;
    CircleShape shape;

    Animation ball;
    ParticleEffect effect;

    float physicalX;
    float physicalY;

    TextureRegion currentFrame;

    public ReboundBall(World world, Animation ball, ParticleEffect effect, float physicalX, float physicalY) {
        this.world = world;
        this.ball = ball;
        this.effect = effect;
        this.physicalX = physicalX;
        this.physicalY = physicalY;

        //创建主角物理模拟
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        shape = new CircleShape();
        shape.setRadius(1.5f/ActConstants.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米

        myFixtureDef.shape = shape;
        myFixtureDef.restitution = 1;
        myFixtureDef.friction = 0;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米
        myBodyDef.fixedRotation = false;


        mySimulation = world.createBody(myBodyDef);
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.MonsterID,"ReboundBall"));
        mySimulation.setGravityScale(1);


        ActConstants.publicInformation.put("ReboundBall",this);





    }




    @Override
    public void act(float delta) {


        super.act(delta);


    }



    @Override
    public void draw(Batch batch, float parentAlpha) {


        super.draw(batch, parentAlpha);


    }
}
