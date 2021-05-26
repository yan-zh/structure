package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import org.graalvm.compiler.lir.LIRInstruction;

public class ApplySkill extends Actor {

    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    Animation prepare;
    Animation fly;
    Animation contact;

    TextureRegion currentFramePrepare;
    TextureRegion currentFrameFly;
    TextureRegion currentFrameContact;


    public boolean state;//true就是存在

    boolean fireMark;
    boolean flyMark;
    boolean contactMark;


    World world;

    static int bulletMark=0;

    float drawX;
    float drawY;

    public ApplySkill(Animation prepare, Animation fly, Animation contact,float x, float y, float[] direction, long actorId) {

        bulletMark++;

        //这里传入参数xy是主角位置，像素，之后根据方向移动一点获得发射位置
        this.setX(x+80*direction[0]);
        this.setY(y+80*direction[1]);

        this.prepare = prepare;
        this.fly = fly;
        this.contact = contact;


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

        fireMark=true;
        flyMark=true;
        contactMark=false;

        Action delayedAction = Actions.run(new Runnable() {
            @Override
            public void run() {
                fireMark=false;
            }
        });

        Action action = Actions.delay(0.3f,delayedAction);//这个数就是1s
        this.addAction(action);


        currentFrameContact = (TextureRegion) contact.getKeyFrames()[0];

    }

    @Override
    public void act(float delta) {

        super.act(delta);
        if(fireMark==true) currentFramePrepare = (TextureRegion) prepare.getKeyFrame(delta,false);

        if(flyMark==true) currentFrameFly = (TextureRegion) fly.getKeyFrame(delta,true);

        if(contactMark==true)currentFrameContact = (TextureRegion) contact.getKeyFrame(delta,false);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        drawX = (mySimulation.getPosition().x-0.7f)*50f;
        drawY = (mySimulation.getPosition().y-0.45f)*50f;

        super.draw(batch, parentAlpha);
        if(fireMark==true) batch.draw(currentFramePrepare,drawX, drawY);

        if(flyMark==true) batch.draw(currentFrameFly,drawX, drawY);

        if(contactMark==true) batch.draw(currentFrameContact,drawX, drawY);



    }

    public void removeBody(){ ActConstants.BodyDeleteList.add(mySimulation); }

    public void contact(){
        contactMark=true;
        flyMark=false;


//        Action delayedAction = Actions.run(new Runnable() {
//            @Override
//            public void run() {
//
//                ApplySkill applySkill = (ApplySkill) ActConstants.publicInformation.get(Integer.toString(bulletMark));
//                ActConstants.publicInformation.remove(Integer.toString(bulletMark));
//                applySkill.remove();
//
//            }
//        });
//
//        Action action = Actions.delay(0.3f,delayedAction);//这个数就是1s
//        this.addAction(action);
    }
}
