package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsUI;

public class BulletSkill extends Actor {

    //the physical simulation of the bullet body
    public Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;
    World world;


    //Animations
    TextureRegion currentFrame;
    Animation contact;
    //frames
    TextureRegion currentFrameContact;

    //flags for control animation play
    boolean fireMark;
    public boolean flyMark;
    public boolean contactMark;

    //stateTimes for animation play
    float stateTime;
    float stateTimeContact;

    //used to distinguish each bullet
    static int bulletMark=0;
    public int myMark;

    //pixel position
    float drawX;
    float drawY;

    //the direction of fire
    float[] direction;

    int damage;

    //flag for deleting
    boolean delete;

    public long actorId;

    int type;//1 feng  2 mu 3 tu 4 jiguang

    public BulletSkill(TextureRegion fly, Animation contact, float x, float y, float[] direction, long actorId, int damage,int type) {
//这里输入的x y是像素
        this.damage = damage;
        this.delete = false;
        this.type =type;
        this.direction = direction;

        this.actorId = actorId;

        bulletMark++;
        myMark = bulletMark;
        //这里传入参数xy是主角位置，像素，之后根据方向移动一点获得发射位置
        this.setX(x+80*direction[0]);
        this.setY(y+80*direction[1]);

        this.currentFrame = fly;
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

        currentFrameContact = (TextureRegion) contact.getKeyFrames()[0];

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


        if(type==1){

            if(flyMark==true) batch.draw(currentFrame,drawX+18, drawY);

            if(contactMark==true) batch.draw(currentFrameContact,drawX-20, drawY-45);


        }else if(type==2){

            if(flyMark==true) batch.draw(currentFrame,drawX+7, drawY-7);

            if(contactMark==true) batch.draw(currentFrameContact,drawX-21, drawY-46);

        }else if(type==3){

            if(flyMark==true) batch.draw(currentFrame,drawX+7, drawY-7);

            if(contactMark==true) batch.draw(currentFrameContact,drawX-21, drawY-46);

        }else if(type==4){

            if(flyMark==true) batch.draw(currentFrame,drawX-190, drawY-120);

            if(contactMark==true) batch.draw(currentFrameContact,drawX-40, drawY-55);

        }
    }

    public void deleteBody(){


        mySimulation.setActive(false);

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
