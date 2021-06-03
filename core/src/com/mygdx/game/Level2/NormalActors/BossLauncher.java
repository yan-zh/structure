package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.CreateBullet;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.UserData;

public class BossLauncher extends Actor {


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

    Body sensorSimulation;
    FixtureDef sensorFixtureDef;
    BodyDef sensorBodyDef;
    CircleShape sensorShape;


    public BossLauncher(World world,float physicalX, float physicalY) {
        //启动一个打的计时器，1秒动一下

        timer = new Timer();
        timerTask = new Timer.Task() {
            @Override

            public void run() {

                synchronized (ActConstants.bossLauncherLock){
                    BossLauncher bossLauncher = ((BossLauncher)ActConstants.publicInformation.get("BossLauncher"));
                    if(bossLauncher!=null){
                        bossLauncher.launch();
                        bossLauncher.remove=true;
                    }
                }
                launch=true;
                System.out.println("launcher");

            }

        };




        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor = false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.groundID,"Ground"));



        //sensor body
        PhysicalEntityDefine.defineStatic();
        sensorBodyDef = PhysicalEntityDefine.getBd();
        sensorFixtureDef = PhysicalEntityDefine.getFd();



        sensorShape = new CircleShape();
        sensorShape.setRadius(1f/ActConstants.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
//        sensorShape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        sensorFixtureDef.shape = sensorShape;

        sensorFixtureDef.isSensor=true;

        sensorBodyDef.position.set(physicalX,physicalY-3);//这个表示物理世界中的米

        sensorSimulation = world.createBody(sensorBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        sensorSimulation.createFixture(sensorFixtureDef).setUserData(new UserData(ActConstants.bossLauncherID,"BossLauncher"));


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

        ActConstants.publicInformation.put("BossLauncher",this);

        launch=false;




    }


    @Override
    public void act(float delta) {
        super.act(delta);

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

    }


    public void adjustPosition(){

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


        CreateBullet createBullet1 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position1.x*50,position1.y*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet2 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position2.x*50,position2.y*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet3 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position3.x*50,position3.y*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet4 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position4.x*50,position4.y*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet5 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position5.x*50,position5.y*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet6 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position6.x*50,position6.y*50,launchDirection,ActConstants.windBulletID,1);
        CreateBullet createBullet7 = new CreateBullet(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.bunny.getAnimCopterRotate,position7.x*50,position7.y*50,launchDirection,ActConstants.windBulletID,1);

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
        synchronized (ActConstants.bossLauncherLock){
            timer.clear();

            DeletePhysicalEntity deletePhysicalEntity1 = new DeletePhysicalEntity();
            deletePhysicalEntity1.deleteBody(mySimulation,world);
            ActConstants.physicalActionList.add(deletePhysicalEntity1);

            ActConstants.publicInformation.remove("BossLauncher");
            return super.remove();
        }
    }


    public void start(){

        if(count==0){
            timer.scheduleTask(timerTask, 1, 3, 500);// 0s之后执行，每次间隔1s，执行20次。
        }

        count=1;
    }
}
