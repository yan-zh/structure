package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.CreateBullet;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.abstraction.UserData;

public class MoveLauncher extends Actor {

    float range;//每次移动距离基数
    float duration;

    Timer MoveTimer;
    Timer.Task MoveTimerTask;

    //************8
    float physicalX;
    float physicalY;


    float MainCharacterX;
    float MainCharacterY;


    Vector2 direction;
    Vector2 directionLeft90;

    Vector2 position1;
    Vector2 position2;
    Vector2 position3;
    Vector2 position4;
    Vector2 position5;
    Vector2 position6;
    Vector2 position7;

    Timer timer;
    Timer.Task timerTask;

    boolean remove;

    int count;

    public boolean launch;


    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;


    boolean die;
    boolean canDamage;

    int health;

    Animation normal;
    Animation goDie;
    Animation attack;

    float normalStateTime;
    float dieStateTime;
    float attackStateTime;
    TextureRegion currentFrame;

    boolean active;





    public MoveLauncher(World world,float physicalX, float physicalY) {

        active = false;

        range=5f;
        duration=0.5f;


        MoveTimer = new Timer();
        MoveTimerTask = new Timer.Task() {
            @Override

            public void run() {
                move();

            }

        };
//        startMove();

        //******************

        canDamage = true;
        die=false;
        health=5;
        normalStateTime = 0;
        dieStateTime = 0;
        attackStateTime = 0;
        this.world = world;

        //启动一个打的计时器，1秒动一下

        timer = new Timer();
        timerTask = new Timer.Task() {
            @Override

            public void run() {

                synchronized (ActConstants.bossLauncherLock){
                    MoveLauncher MoveLauncher = ((MoveLauncher)ActConstants.publicInformation.get("moveLauncher"));
                    if(MoveLauncher!=null){
                        MoveLauncher.launch();
                        System.out.println("launcher");
                    }
                }
                launch=true;


            }

        };


        this.setX(physicalX*50);
        this.setY(physicalY*50);


        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(3f/ ActConstants.worldSize_shapeAndPhysics,3f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor = false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.moveLauncherID,"moveLauncher"));




        this.physicalX = physicalX;
        this.physicalY = physicalY;
        this.MainCharacterX = ActConstants.mainCharacter.getPhysicalX();
        this.MainCharacterY = ActConstants.mainCharacter.getPhysicalY();
        this.direction = new Vector2();
        this.directionLeft90 = new Vector2();

        this.position1 = new Vector2();
        this.position2 = new Vector2();
        this.position3 = new Vector2();
        this.position4 = new Vector2();
        this.position5 = new Vector2();
        this.position6 = new Vector2();
        this.position7 = new Vector2();



        count=0;

        adjustPosition();

        ActConstants.publicInformation.put("moveLauncher",this);

        launch=false;


        normal = Assets.instance.bunny.animNormal;
        goDie = Assets.instance.bunny.getAnimCopterRotate;
        attack = Assets.instance.mainCharacter.animRun;

        currentFrame = (TextureRegion) normal.getKeyFrame(0);




    }


    @Override
    public void act(float delta) {
        super.act(delta);



        if(die==false){
            if(active==true){
                mySimulation.setTransform(new Vector2(getX()/50,getY()/50),0);
                attackStateTime += delta;
                currentFrame = (TextureRegion) attack.getKeyFrame(attackStateTime,true);
            }else{
                mySimulation.setTransform(new Vector2(getX()/50,getY()/50),0);
                normalStateTime += delta;
                currentFrame = (TextureRegion) normal.getKeyFrame(normalStateTime,true);
            }

        }else{
            dieStateTime += delta;
            currentFrame = (TextureRegion) goDie.getKeyFrame(dieStateTime,false);
            if(goDie.isAnimationFinished(dieStateTime)){
                remove();
            }
        }




//
//        System.out.println(mySimulation.getPosition());

        ;

//        if(count==0&&launch==true){
//            launch();
//        }
//
//        count=1;



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);


    }


    public void adjustPosition(){

        physicalX = mySimulation.getPosition().x;
        physicalY = mySimulation.getPosition().y;

        this.MainCharacterX = ActConstants.mainCharacter.getPhysicalX();
        this.MainCharacterY = ActConstants.mainCharacter.getPhysicalY();

        float[] standard = MyVector.getStandardVector(physicalX,physicalY,MainCharacterX,MainCharacterY);

        direction.x = standard[0];
        direction.y = standard[1];

        Vector2 myPosition = new Vector2();
        myPosition.set(physicalX,physicalY);


        Vector2 directionTemp = direction.cpy();
        directionTemp.scl(2);

        System.out.println(directionTemp.x);

        directionLeft90.x = directionTemp.y;
        directionLeft90.y = -directionTemp.x;
        directionLeft90.setLength(1);

        directionLeft90.scl(3);
        position1.setZero();
        position1 = directionTemp.cpy();
        position1.add(directionLeft90);
        position1.add(myPosition);
        directionLeft90.setLength(1);

        directionLeft90.scl(2);
        position2.setZero();
        position2 = directionTemp.cpy();
        position2.add(directionLeft90);
        position2.add(myPosition);
        directionLeft90.setLength(1);

        directionLeft90.scl(1);
        position3.setZero();
        position3 = directionTemp.cpy();
        position3.add(directionLeft90);
        position3.add(myPosition);
        directionLeft90.setLength(1);

        position4.setZero();
        position4 = directionTemp.cpy();
        position4.add(myPosition);

        directionLeft90.scl(-1);
        position5.setZero();
        position5 = directionTemp.cpy();
        position5.add(directionLeft90);
        position5.add(myPosition);
        directionLeft90.scl(-1);

        directionLeft90.scl(-2);
        position6.setZero();
        position6 = directionTemp.cpy();
        position6.add(directionLeft90);
        position6.add(myPosition);
        directionLeft90.scl((float)-0.5);

        directionLeft90.scl(-3);
        position7.setZero();
        position7 = directionTemp.cpy();
        position7.add(directionLeft90);
        position7.add(myPosition);
        directionLeft90.setLength(1);
        directionLeft90.scl(-1);

        directionTemp.setLength(1);

        System.out.println(position7);
//
//        directionLeft90.scl(3);
//        System.out.println(direction.x);


//        position1 = direction.scl(2).add(directionLeft90.scl(3));
//        position2 = direction.scl(2).add(directionLeft90.scl(2));
//        position3 = direction.scl(2).add(directionLeft90.scl(1));
//        position4 = direction.scl(2);
//        position5 = direction.scl(2).add(directionLeft90.scl(-1));
//        position6 = direction.scl(2).add(directionLeft90.scl(-2));
//        position7 = direction.scl(2).add(directionLeft90.scl(-3));
//
//        System.out.println(direction.x);
    }

    public void launch(){

        adjustPosition();
        float[] launchDirection = new float[2];
        launchDirection[0] = direction.x;
        launchDirection[1] = direction.y;
        System.out.println(direction.x+"  "+direction.y);


        CreateBullet createBullet1 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,(position1.x)*50,(position1.y)*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet2 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,(position2.x)*50,(position2.y)*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet3 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,(position3.x)*50,(position3.y)*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet4 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,(position4.x)*50,(position4.y)*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet5 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,(position5.x)*50,(position5.y)*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet6 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,(position6.x)*50,(position6.y)*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet7 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,(position7.x)*50,(position7.y)*50,launchDirection,ActConstants.windBulletID,1);

        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(createBullet1);
            ActConstants.physicalActionList.add(createBullet2);
            ActConstants.physicalActionList.add(createBullet3);
            ActConstants.physicalActionList.add(createBullet4);
            ActConstants.physicalActionList.add(createBullet5);
            ActConstants.physicalActionList.add(createBullet6);
            ActConstants.physicalActionList.add(createBullet7);
        }

//        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position2.x*50,position2.y*50,launchDirection,ActConstants.windBulletID,1));
//        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position3.x*50,position3.y*50,launchDirection,ActConstants.windBulletID,1));
//        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position4.x*50,position4.y*50,launchDirection,ActConstants.windBulletID,1));
//        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position5.x*50,position5.y*50,launchDirection,ActConstants.windBulletID,1));
//        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position6.x*50,position6.y*50,launchDirection,ActConstants.windBulletID,1));
//        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position7.x*50,position7.y*50,launchDirection,ActConstants.windBulletID,1));

    }

    @Override
    public boolean remove() {
        if(count==0){


            DeletePhysicalEntity deletePhysicalEntity1 = new DeletePhysicalEntity();
            deletePhysicalEntity1.deleteBody(mySimulation,world);
            ActConstants.physicalActionList.add(deletePhysicalEntity1);

            ActConstants.publicInformation.remove("moveLauncher");


        }
        count = 1;
        return super.remove();
    }


    public void start(){

        timer.scheduleTask(timerTask, 1, 3, 500);// 0s之后执行，每次间隔1s，执行20次。
        active = true;
    }

//    public void stop(){
//        if(count==0) {
//
//            timerTask.cancel();
//            timer.stop();
//            timer.clear();
//        }
//
//        count=1;
//
//    }


    private void move(){
        float distance=range+ MathUtils.random(-1f,1f);
        distance=distance*MathUtils.randomSign()*50;
        MoveByAction action= Actions.moveBy(distance,0,duration);
        this.addAction(action);
    }

    public void startMove(){
        MoveTimer.scheduleTask(MoveTimerTask, 0, 3, 100);
    }

    public void stopTimer(){

        timerTask.cancel();
        timer.clear();

        MoveTimerTask.cancel();
        MoveTimer.clear();
    }

    public void damage(int damage){
        if(canDamage){
            health = health-damage;
            if(health<=0){
                die=true;
                canDamage = false;
                stopTimer();
            }
        }

    }
}
