package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.DieAction;
import com.mygdx.game.abstraction.UserData;

import java.util.ArrayList;

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

    Animation walkLeft;
    Animation walkRight;
    Animation standRight;
    Animation standLeft;
    Animation dieLeft;
    Animation dieRight;

    ArrayList<Animation> animationArrayListLeft;
    ArrayList<Animation> animationArrayListRight;
    ArrayList<ArrayList> arrayListArrayList;

    short action;//0 停 1 左 2 右移动方向
    short direction;//0 左 1 右 图片朝向   两个全是3就是die

    float speed;

    TextureRegion currentFrame;

    int life;

    short myNumber;

    public boolean move;

    int count;



    public MonsterA(World world, float x, float y,Animation walkLeft, Animation walkRight, Animation standLeft, Animation standRight,Animation dieLeft,Animation dieRight) {

        this.standLeft = standLeft;
        this.standRight = standRight;
        this.walkLeft = walkLeft;
        this.walkRight = walkRight;
        this.dieLeft = dieLeft;
        this.dieRight = dieRight;

        life=5;

        count=0;

        animationArrayListLeft = new ArrayList<>();

        animationArrayListLeft.add(standLeft);
        animationArrayListLeft.add(walkLeft);
        animationArrayListLeft.add(dieLeft);

        animationArrayListRight = new ArrayList<>();
        animationArrayListRight.add(standRight);
        animationArrayListRight.add(walkRight);
        animationArrayListRight.add(dieRight);


        arrayListArrayList = new ArrayList<>();
        arrayListArrayList.add(animationArrayListLeft);
        arrayListArrayList.add(animationArrayListRight);

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
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.MonsterID,"Monster"+myNumber));



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




        //启动一个走的计时器，3秒动一下（内部再启动一个，0.5秒后停止）

        //启动一个打的计时器，1秒动一下

//        Timer timer = new Timer();
//        Timer.Task timerTask = new Timer.Task() {
//            @Override
//
//            public void run() {
//
//                MonsterA monsterA = (MonsterA)ActConstants.publicInformation.get("Monster"+number);
//                monsterA.attack();
//
//            }
//
//        };
//        timer.scheduleTask(timerTask, 3, 2, 20);// 0s之后执行，每次间隔1s，执行20次。

        number++;
    }

    public void start(){

        if(count==0){
            Timer timer = new Timer();
            Timer.Task timerTask = new Timer.Task() {
                @Override

                public void run() {
                    synchronized (ActConstants.MonsterActionLock){
                        MonsterA monsterA = (MonsterA)ActConstants.publicInformation.get("Monster"+myNumber);
                        if(monsterA!=null){
                            monsterA.attack();
                            monsterA.move=true;
                        }
                    }
                }

            };
            timer.scheduleTask(timerTask, 1, 3, 100);// 0s之后执行，每次间隔1s，执行20次。
        }


        count=1;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        //运动状态改变
        if(action==1){
            mySimulation.setLinearVelocity(-speed,mySimulation.getLinearVelocity().y);
            

        }else if(action==2){
            mySimulation.setLinearVelocity(speed,mySimulation.getLinearVelocity().y);
        }

        sensorSimulation.setLinearVelocity(mySimulation.getLinearVelocity());


        Animation currentAnimation = (Animation) (arrayListArrayList.get(direction).get(action));

        currentFrame = (TextureRegion) currentAnimation.getKeyFrame(statetime,true);

        if(move){
            MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));
            if(mySimulation.getPosition().x>=mainCharacter.getPhysicalX()){
                action=1;
                direction=0;
            }else{
                action=2;
                direction=1;
            }
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);



        batch.draw(currentFrame,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);

    }


    @Override
    public boolean remove() {
        synchronized (ActConstants.MonsterActionLock){
            MyGdxGame.currentStage.addActor(new DieAction(Assets.instance.bunny.animNormal,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f));
            //消除自身，要锁

            DeletePhysicalEntity deletePhysicalEntity1 = new DeletePhysicalEntity();
            deletePhysicalEntity1.deleteBody(mySimulation,world);

            DeletePhysicalEntity deletePhysicalEntity2 = new DeletePhysicalEntity();
            deletePhysicalEntity2.deleteBody(sensorSimulation,world);

            DeletePhysicalEntity deletePhysicalEntity3 = new DeletePhysicalEntity();
            deletePhysicalEntity3.deleteJoint(weldJoint,world);


            ActConstants.physicalActionList.add(deletePhysicalEntity3);
            ActConstants.physicalActionList.add(deletePhysicalEntity2);
            ActConstants.physicalActionList.add(deletePhysicalEntity1);
            ActConstants.publicInformation.remove("Monster"+myNumber);
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
        MainCharacter mainCharacter = (MainCharacter)ActConstants.publicInformation.get("MainCharacter");
        float[] direction = MyVector.getStandardVector(mySimulation.getPosition().x,mySimulation.getPosition().y,mainCharacter.getPhysicalX(),mainCharacter.getPhysicalY());
        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animRun,this.getX(),this.getY(),direction,ActConstants.windBulletID,1));
    }
}
