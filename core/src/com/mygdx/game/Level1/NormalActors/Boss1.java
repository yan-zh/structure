package com.mygdx.game.Level1.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.abstraction.UserData;

public class Boss1 extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;

    Body sensorSimulation;
    FixtureDef sensorFixtureDef;
    BodyDef sensorBodyDef;
    PolygonShape sensorshape;


    float statetime;//用于替换主角动作图片的标记
    float statetime2;

    Animation rest;
    Animation purchase;
    Animation weakUp;
    TextureRegion currentFrame;

    boolean actionState1;
    boolean actionState2;

    float physicalX;
    float physicalY;

    float physicalBX;
    float physicalBY;

    boolean reBorn;

    public Boss1(World world, float x, float y) {

        reBorn = false;

        //获得物理世界引用
        this.world = world;

        //创建boss
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();
        myFixtureDef.isSensor = true;
        myBodyDef.gravityScale = 0;



        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(12f/ ActConstants.worldSize_shapeAndPhysics,9f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米
        physicalX = x;
        physicalY = y;
        physicalBX = x;
        physicalBY = y;

        mySimulation = world.createBody(myBodyDef);

        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.Boss1ID,"Boss1"));




        //创建sensor
        PhysicalEntityDefine.defineStatic();
        sensorBodyDef = PhysicalEntityDefine.getBd();
        sensorFixtureDef = PhysicalEntityDefine.getFd();

        sensorFixtureDef.isSensor = true;






        sensorshape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        sensorshape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,5f/ ActConstants.worldSize_shapeAndPhysics);
        sensorFixtureDef.shape = sensorshape;

        sensorBodyDef.position.set(x+10,y-10);//这个表示物理世界中的米

        sensorSimulation = world.createBody(sensorBodyDef);

        sensorSimulation.createFixture(sensorFixtureDef).setUserData(new UserData(ActConstants.Boss1SensorID,"Boss1"));


        //内存显示区
        this.statetime = 0;
        this.statetime2 = 0;

        //交互注册
        ActConstants.publicInformation.put("Boss1",this);

        rest = AssetsLevel1.instance.cdboss.animBreath;
        purchase = AssetsLevel1.instance.cdboss.animAttackA;
        weakUp = AssetsLevel1.instance.cdboss.animDamage;

        actionState1 = true;//true是等待，false是追逐
        actionState2 = false;//true是播放weakup





    }

    @Override
    public void act(float delta) {
        super.act(delta);

        physicalX = mySimulation.getPosition().x;
        physicalY = mySimulation.getPosition().y;

        statetime += delta;

        if(actionState1==true){
            currentFrame = (TextureRegion) rest.getKeyFrame(statetime,true);
        }else if(actionState2==true){
            statetime2+=delta;
            currentFrame = (TextureRegion) weakUp.getKeyFrame(statetime2,false);
            if(weakUp.isAnimationFinished(statetime2)){
                actionState2=false;

            }
        }else{
            currentFrame = (TextureRegion) purchase.getKeyFrame(statetime,true);
            MainCharacter mainCharacter = (MainCharacter)ActConstants.publicInformation.get("MainCharacter");
            float mainCharacterX = mainCharacter.getPhysicalX();
            float mainCharacterY = mainCharacter.getPhysicalY();
            float myX = mySimulation.getPosition().x;
            float myY = mySimulation.getPosition().y-3;
            float[] direction = MyVector.getStandardVector(myX,myY,mainCharacterX,mainCharacterY);
            if((mainCharacterX-myX)<=17) {
                mySimulation.setLinearVelocity(direction[0] * 2, direction[1]);
            }else{
                mySimulation.setLinearVelocity(direction[0]*10,direction[1]*5);
            }


        }

        if(reBorn ==true){
            mySimulation.setTransform(physicalBX,physicalBY,0);
            reBorn = false;
        }



    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);



        if(ActConstants.mainCharacter.getPhysicalX()>=this.physicalX) {
            if (!currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }else{
            if (currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }

        batch.draw(currentFrame,(mySimulation.getPosition().x-7.5f)*50f, (mySimulation.getPosition().y-5.5f)*50f);
    }

    public void active(){
        actionState1 = false;
        actionState2 = true;
    }

    public void setReBorn(boolean set){
       reBorn = set;
    }
}
