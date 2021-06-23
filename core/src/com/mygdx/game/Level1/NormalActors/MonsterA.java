package com.mygdx.game.Level1.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.MainCharacter;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Level2.PhysicalActions.MonsterAttack;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.DieAction;
import com.mygdx.game.abstraction.UserData;

public class MonsterA extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;


    Body sensorSimulation;
    FixtureDef sensorFixtureDef;
    BodyDef sensorBodyDef;
    CircleShape sensorShape;

    WeldJointDef weldJointDef;
    Joint weldJoint;

    static short number=0;


    float statetime;

    Animation walk;

    Animation die;



    short action;//0 stop 1 left 2 right(move direction)
    short direction;//0 left 1 right both 3 die(picture direction)

    float speed;

    TextureRegion currentFrame;

    int life;

    short myNumber;

    public boolean move;

    int count;

    Timer timer;
    Timer.Task timerTask;

    int type;

    public boolean remove;

    float dieTime;

    float physicalX;
    float physicalY;



    public MonsterA(World world, float x, float y,int type,Animation walk, Animation die) {

        this.physicalX = x;
        this.physicalY = y;

        this.dieTime = 0;
        this.remove = false;
        this.type = type;

        //after contact, wait some time to attack
        timer = new Timer();
        timerTask = new Timer.Task() {
            @Override

            public void run() {
                synchronized (ActConstants.MonsterActionLock){
                    MonsterA monsterA = (MonsterA)ActConstants.publicInformation.get("Monster"+myNumber);
                    if(monsterA!=null){
                        monsterA.attack();
                        monsterA.move=true;
                        AudioManager.instance.play(AssetsUI.instance.sounds.bullet_Monster_Emmit);
                    }
                }
            }

        };



        this.walk = walk;
        this.die = die;

        life=5;

        count=0;

        speed = 1;



        this.world = world;


        //physical entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        myNumber=number;

        shape = new PolygonShape();
        shape.setAsBox(2.5f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(x,y);


        mySimulation = world.createBody(myBodyDef);
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.MonsterID,"Monster"+myNumber));
        mySimulation.setGravityScale(1);



        //sensor body
        PhysicalEntityDefine.defineCharacter();
        sensorBodyDef = PhysicalEntityDefine.getBd();
        sensorFixtureDef = PhysicalEntityDefine.getFd();



        sensorShape = new CircleShape();
        sensorShape.setRadius(5f/ActConstants.worldSize_shapeAndPhysics);
        sensorFixtureDef.shape = sensorShape;

        sensorFixtureDef.isSensor=true;
        sensorFixtureDef.density = 0;

        sensorBodyDef.position.set(x,y);

        sensorSimulation = world.createBody(sensorBodyDef);

        sensorSimulation.createFixture(sensorFixtureDef).setUserData(new UserData(ActConstants.monsterSensorID,"Monster"+myNumber));
        sensorSimulation.setGravityScale(0);


        weldJointDef = new WeldJointDef();
        weldJointDef.initialize(mySimulation,sensorSimulation,mySimulation.getPosition());
        weldJoint = world.createJoint(weldJointDef);


        this.statetime = 0;


        //register
        ActConstants.publicInformation.put("Monster"+myNumber,this);


        action=0;
        direction=0;




        number++;
    }

    public void start(){

        if(count==0){

            timer.scheduleTask(timerTask, 1, 3, 100);// after 0s action，1s delay，do 20 times

        }

        count=1;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        this.physicalX = mySimulation.getPosition().x;
        this.physicalY = mySimulation.getPosition().y;

        //close to mainActor
        if(move){
            MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));
            if(mySimulation.getPosition().x>=mainCharacter.getPhysicalX()){
                mySimulation.setLinearVelocity(-speed,mySimulation.getLinearVelocity().y);
            }else{
                mySimulation.setLinearVelocity(speed,mySimulation.getLinearVelocity().y);
            }
        }


        sensorSimulation.setLinearVelocity(mySimulation.getLinearVelocity());


        if(remove == false){
            currentFrame = (TextureRegion) walk.getKeyFrame(statetime,true);
        }else{
            dieTime += delta;
            currentFrame = (TextureRegion) die.getKeyFrame(dieTime,false);
            if(die.isAnimationFinished(dieTime)){
                remove();
            }

        }




    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(ActConstants.mainCharacter.getPhysicalX()<=this.physicalX) {
            if (!currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }else{
            if (currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }

        batch.draw(currentFrame,(mySimulation.getPosition().x-1.25f)*50f, (mySimulation.getPosition().y-0.75f)*50f);

    }


    @Override
    public boolean remove() {

        //remove both sensor, body and joint
        synchronized (ActConstants.MonsterActionLock){
            MyGdxGame.currentStage.addActor(new DieAction(AssetsUI.instance.cdxgw.animDead,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f));


            DeletePhysicalEntity deletePhysicalEntity1 = new DeletePhysicalEntity();
            deletePhysicalEntity1.deleteBody(mySimulation,world);

            DeletePhysicalEntity deletePhysicalEntity2 = new DeletePhysicalEntity();
            deletePhysicalEntity2.deleteBody(sensorSimulation,world);

            DeletePhysicalEntity deletePhysicalEntity3 = new DeletePhysicalEntity();
            deletePhysicalEntity3.deleteJoint(weldJoint,world);


            synchronized (ActConstants.physicalActionListLock){
                ActConstants.physicalActionList.add(deletePhysicalEntity3);
                ActConstants.physicalActionList.add(deletePhysicalEntity2);
                ActConstants.physicalActionList.add(deletePhysicalEntity1);
            }


            synchronized (ActConstants.publicInformationLock){
                ActConstants.publicInformation.remove("Monster"+myNumber);
            }

            timerTask.cancel();
            timer.clear();
            return super.remove();
        }
    }


    public boolean damage(long bulletContactID,int bulletDamage){
        life -= bulletDamage;
        if(life<=0){
            return false;
        }else{
            return true;
        }

    }

    public float getX(){
        return mySimulation.getPosition().x*ActConstants.worldSize_pAndPhysic;
    }

    public float getY(){
        return mySimulation.getPosition().y*ActConstants.worldSize_pAndPhysic;
    }

    public void attack(){
        synchronized (ActConstants.physicalActionListLock){
            MonsterAttack monsterAttack = new MonsterAttack(mySimulation,getX(),getY(),type);
            ActConstants.physicalActionList.add(monsterAttack);
        }


    }

    public void repulse(float x, float y){
        if(x<=mySimulation.getPosition().x){
            mySimulation.applyLinearImpulse(200,400,mySimulation.getPosition().x,mySimulation.getPosition().y,true);
        }else{
            mySimulation.applyLinearImpulse(-200,400,mySimulation.getPosition().x,mySimulation.getPosition().y,true);
        }
    }
}
