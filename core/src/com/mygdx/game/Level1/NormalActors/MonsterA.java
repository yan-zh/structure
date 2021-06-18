package com.mygdx.game.Level1.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
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


    float statetime;//用于替换主角动作图片的标记

    Animation walk;

    Animation die;



    short action;//0 停 1 左 2 右移动方向
    short direction;//0 左 1 右 图片朝向   两个全是3就是die

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
        //获得物理世界引用


        //创建主角物理模拟
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        myNumber=number;

        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(2.5f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米


        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.MonsterID,"Monster"+myNumber));
        mySimulation.setGravityScale(1);



        //sensor body
        PhysicalEntityDefine.defineCharacter();
        sensorBodyDef = PhysicalEntityDefine.getBd();
        sensorFixtureDef = PhysicalEntityDefine.getFd();



        sensorShape = new CircleShape();
        sensorShape.setRadius(5f/ActConstants.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
//        sensorShape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        sensorFixtureDef.shape = sensorShape;

        sensorFixtureDef.isSensor=true;
        sensorFixtureDef.density = 0;

        sensorBodyDef.position.set(x,y);//这个表示物理世界中的米

        sensorSimulation = world.createBody(sensorBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        sensorSimulation.createFixture(sensorFixtureDef).setUserData(new UserData(ActConstants.monsterSensorID,"Monster"+myNumber));
        sensorSimulation.setGravityScale(0);


        weldJointDef = new WeldJointDef();
        weldJointDef.initialize(mySimulation,sensorSimulation,mySimulation.getPosition());
        weldJoint = world.createJoint(weldJointDef);

        //内存显示区
        this.statetime = 0;


        //交互注册
        ActConstants.publicInformation.put("Monster"+myNumber,this);


        action=0;
        direction=0;




        number++;
    }

    public void start(){

        if(count==0){

            timer.scheduleTask(timerTask, 1, 3, 100);// 0s之后执行，每次间隔1s，执行20次。

        }

        count=1;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        this.physicalX = mySimulation.getPosition().x;
        this.physicalY = mySimulation.getPosition().y;

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
        synchronized (ActConstants.MonsterActionLock){
            MyGdxGame.currentStage.addActor(new DieAction(AssetsUI.instance.cdxgw.animDead,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f));
            //消除自身，要锁

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

    //true是还活着，这个bulletID有用的，判断伤害加成用的
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
//        MainCharacter mainCharacter = (MainCharacter)ActConstants.publicInformation.get("MainCharacter");
//        float[] direction = MyVector.getStandardVector(mySimulation.getPosition().x,mySimulation.getPosition().y,mainCharacter.getPhysicalX(),mainCharacter.getPhysicalY());
//        if(type==1){
//            MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animRun,this.getX(),this.getY(),direction,ActConstants.windBulletID,1));
//        }else if(type==2){
//            MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.mainCharacter.animBreath,Assets.instance.mainCharacter.animRun,this.getX(),this.getY(),direction,ActConstants.windBulletID,1));
//        }else if(type==3){
//            MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.getAnimCopterRotate,Assets.instance.mainCharacter.animRun,this.getX(),this.getY(),direction,ActConstants.windBulletID,1));
//        }
        //MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animRun,this.getX(),this.getY(),direction,ActConstants.windBulletID,1));
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
