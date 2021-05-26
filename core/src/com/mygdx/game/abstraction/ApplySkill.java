package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import org.graalvm.compiler.lir.LIRInstruction;

public class ApplySkill extends Actor {

    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    Animation wait;
    Animation absorb;

    TextureRegion currentFrame;

    public boolean state;//true就是存在

    float statetime;


    World world;

    static int bulletMark=0;

    public ApplySkill(float x, float y, float[] direction, long actorId) {

        bulletMark++;

        //这里传入参数xy是主角位置，像素，之后根据方向移动一点获得发射位置
        this.setX(x+80*direction[0]);
        this.setY(y+80*direction[1]);


        //物理实体创建
        PhysicalEntityDefine.defineAttack();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        CircleShape shape = new CircleShape();//似乎是一个像素一个
        shape.setRadius(0.5f/ActConstants.worldSize_shapeAndPhysics);

        myFixtureDef.shape = shape;

        myBodyDef.position.set(this.getX()/ActConstants.worldSize_pAndPhysic,this.getY()/ActConstants.worldSize_pAndPhysic);//这个表示物理世界中的米

        this.world = ((MyStage)ActConstants.publicInformation.get("CurrentStage")).world;

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        myFixture = mySimulation.createFixture(myFixtureDef);
        myFixture.setUserData(new UserData(actorId,Integer.toString(bulletMark)));

        ActConstants.publicInformation.put(Integer.toString(bulletMark),this);
        mySimulation.setGravityScale(0);

        mySimulation.setLinearVelocity(8*direction[0],8*direction[1]);



    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);

    }

    public void removeBody(){ ActConstants.BodyDeleteList.add(mySimulation); }
}
