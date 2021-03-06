package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.PhysicalEntityDefine;

public class BulletSkill extends Actor {

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
    public boolean flyMark;
    public boolean contactMark;

    float stateTime;
    float stateTimeContact;

    World world;

    static int bulletMark=0;

    public int myMark;

    short count;

    float drawX;
    float drawY;

    float[] direction;

    int damage;

    boolean delete;

    public BulletSkill(Animation prepare, Animation fly, Animation contact, float x, float y, float[] direction, long actorId, int damage) {
//这里输入的x y是像素
        this.damage = damage;
        this.delete = false;

        this.direction = direction;

        bulletMark++;
        myMark = bulletMark;
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

        synchronized (ActConstants.publicInformationLock){
            this.world = ((MyStage)ActConstants.publicInformation.get("CurrentStage")).world;
        }

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        myFixture = mySimulation.createFixture(myFixtureDef);
        myFixture.setUserData(new UserData(actorId,Integer.toString(myMark)));

        synchronized (ActConstants.publicInformationLock){
            ActConstants.publicInformation.put(Integer.toString(myMark),this);
        }

        mySimulation.setGravityScale(0);

        mySimulation.setLinearVelocity(8*direction[0],8*direction[1]);

        fireMark=true;
        flyMark=true;
        contactMark=false;

        stateTime=0;
        stateTimeContact=0;
        count=0;
        currentFrameContact = (TextureRegion) contact.getKeyFrames()[0];
        currentFramePrepare = (TextureRegion) prepare.getKeyFrame(0);
        currentFrameFly = (TextureRegion) fly.getKeyFrame(0);

        drawX = (mySimulation.getPosition().x-0.7f)*50f;
        drawY = (mySimulation.getPosition().y-0.45f)*50f;

    }

    @Override
    public void act(float delta) {
        if(contactMark==false){
            drawX = (mySimulation.getPosition().x-0.7f)*50f;
            drawY = (mySimulation.getPosition().y-0.45f)*50f;
        }

        stateTime += delta;


        super.act(delta);
        if(fireMark==true) currentFramePrepare = (TextureRegion) prepare.getKeyFrame(stateTime,false);
        if(prepare.isAnimationFinished(stateTime)) fireMark=false;

        if(flyMark==true) currentFrameFly = (TextureRegion) fly.getKeyFrame(stateTime,true);

        if(contactMark==true){
            stateTimeContact +=delta;
            currentFrameContact = (TextureRegion) contact.getKeyFrame(stateTimeContact,false);

        }

        synchronized (ActConstants.bulletDeleteLock){
            if(delete==true){
                deleteBody();
                delete=false;
            }
        }


        if(contact.isAnimationFinished(stateTimeContact)){
            synchronized (ActConstants.publicInformationLock){
                ActConstants.publicInformation.remove(myMark);
            }

            this.remove();
        }


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        if(fireMark==true){
        float cos = MyVector.countAngle(0,1,direction[0],direction[1]);
        float angle = (float)Math.acos(cos);
        if(direction[0]<0){
            angle = -angle;
        }
            batch.draw(currentFramePrepare,
                    drawX,drawY,
                    (float)0.5*currentFramePrepare.getRegionWidth(),(float)0.5*currentFramePrepare.getRegionHeight(),currentFramePrepare.getRegionWidth(),
                    currentFramePrepare.getRegionWidth(),(float)1.0,(float)1.0,angle,true);
        }


        if(flyMark==true) batch.draw(currentFrameFly,drawX, drawY);

        if(contactMark==true) batch.draw(currentFrameContact,drawX, drawY);

    }

    public void deleteBody(){
        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(mySimulation,world);
        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(deletePhysicalEntity);
        }

    }

    public int getDamage(){return damage;}

    public void dispose(){
        this.contactMark=true;
        this.flyMark=false;
        synchronized (ActConstants.bulletDeleteLock){
            this.delete = true;
        }

        //this.deleteBody();


    }

}
