package com.mygdx.game.Level3;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.ContactReactions.BulletAndMonsterA;
import com.mygdx.game.Level1.ContactReactions.MonsterASensorAndMainCharacter;
import com.mygdx.game.Level1.NormalActors.ChangeCamera;
import com.mygdx.game.Level2.NormalActors.*;
import com.mygdx.game.Level3.ContactReactions.*;
import com.mygdx.game.Level3.NormalActors.*;
import com.mygdx.game.Listeners.PhysicalContactListener;
import com.mygdx.game.Listeners.UserInputListener;
import com.mygdx.game.PublicContactReaction.*;
import com.mygdx.game.Tools.*;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.*;


public class Stage3 extends MyStage {


    Box2DDebugRenderer boxRender;//
    OrthographicCamera cameraPhysic;//
    OrthographicCamera stageCamera;//

    CameraFocus cameraFocus;
    //
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;


    ChangeCamera changeCamera;

    public Stage3(InputMultiplexer inputMultiplexer){

        AssetsLevel2.instance.init(new AssetManager());
//        AssetsLevel0.instance.init(new AssetManager());

        ActConstants.changeStageTo = 0;

//        AudioManager.instance.play(AssetsLevel0.instance.music.foresttheme);

        cUp=110;
        cDown=-300000;
        cleft=-300000;
        cright=300000;

        AssetsUI.instance.addSprit();
        AssetsUI.instance.addSprit();


        System.out.println("新建了一个Stage3");
        inputMultiplexer.addProcessor(this);

        world = new World(new Vector2(0,-10),true);
        world.setContactListener(new PhysicalContactListener());
        PhysicalEntityDefine.boundWorld(world);

        this.addActor(new MainCharacter(world,35,60));//
        this.addActor(new Beacon(35, 60, ActConstants.beaconID, world, "Beacon"));
        this.addActor(new Bubbles(world,745f,31f,25f,1f,ActConstants.BubbleID,"bubbles"));
        new BubbleAndCharacter();

        boxRender = new Box2DDebugRenderer();
        cameraPhysic = new OrthographicCamera();//
        cameraPhysic.setToOrtho(false, (ActConstants.SCREEN_WIDTH)/ ActConstants.worldSize_pAndPhysic,(ActConstants.SCREEN_HEIGHT)/ ActConstants.worldSize_pAndPhysic);//这个是摄像机的框的大小，摄像机取景后还要经过最外层界面的压缩得到最终效果
        stageCamera = (OrthographicCamera) this.getCamera();
        stageCamera.setToOrtho(false, ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);

        //
        cameraFocus = new CameraFocus(cameraPhysic, stageCamera);




        //
        this.addListener(new UserInputListener());//


        tiledMap = new TmxMapLoader().load("core/assets/bing/bing.tmx");
        //
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //
        LoadTiledMap.doScene2(world,this,tiledMap);

        //

        //
        cameraFocus.focusOn(205.6f,80,1);//这里单位是米


        ActConstants.publicInformation.replace("CurrentStage",this);


        changeCamera = new ChangeCamera(cameraPhysic,stageCamera);

        changeCamera.leave();


        changeCamera.act();

//        AudioManager.instance.play(AssetsLevel1.instance.music.foresttheme);

        //public contact

//       new MainCharacterAndSpine();

        new BulletAndMonsterA();
        new MonsterASensorAndMainCharacter();
        new PortalAndMainCharacter();

        new GroundAndMainCharacter();
        new MainCharacterAndSpine();


        new BulletAndMonsterA();
        new MonsterASensorAndMainCharacter();

        new BulletDispose();
        new BulletAndMain();

        new MainCharacterAndBullet();

        //***********************************

//
        batch = new SpriteBatch();
//
        cameraGUI = new OrthographicCamera(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);
        cameraGUI.position.set(0,0,0);
        cameraGUI.setToOrtho(true);
//
        cameraGUI.update();

//yzh***************************************************



        //region 1


        this.addActor(new Fairy(3, AssetsUI.instance.txjling.animBreath,AssetsUI.instance.txjling.animAttack, (int) (182*50),38*50,ActConstants.sandFairyID,world,"SandFairy"));
        new SandFairyAndMainCharacter();



        this.addActor(new Spine(world,154.3f,56.75f,3.5f,1f));
        this.addActor(new Spine(world,159.8f,55.3f,6.4f,1f));


        this.addActor(new Spine(world,236f,78.925f,2.35f,5f));

        this.addActor(new Spine(world,214.25f,79.49f,3.72f,4.5f));
        this.addActor(new Spine(world,218.58f,80f,3.72f,4.5f));
        this.addActor(new Spine(world,222f,84f,3.72f,4.5f));
        this.addActor(new Spine(world,226.2f,86.2f,3.72f,4.5f));
        this.addActor(new Spine(world,230.5f,87.3f,3.72f,4.5f));
        this.addActor(new Spine(world,233.8f,88.3f,3.72f,4.5f));
        this.addActor(new Spine(world,238.1f,88.6f,3.72f,4.5f));
        this.addActor(new Spine(world,242f,88.6f,3.72f,4.5f));
        this.addActor(new Spine(world,243.4f,92.5f,3.72f,4.5f));
        this.addActor(new Spine(world,245f,96.6f,3.72f,4.5f));
        this.addActor(new Spine(world,246.1f,100.5f,3.72f,4.5f));
        this.addActor(new Spine(world,249.6f,109.15f,3.72f,4.5f));
        this.addActor(new Spine(world,248.6f,113.5f,3.72f,4.5f));
        this.addActor(new Spine(world,218.5f,101.4f,3.72f,4.5f));
        this.addActor(new Spine(world,220.5f,105f,3.72f,4.5f));
        this.addActor(new Spine(world,222f,109.1f,3.72f,4.5f));
        this.addActor(new Spine(world,224.86f,102.13f,3.72f,4.5f));
        this.addActor(new Spine(world,226f,105.8f,3.72f,4.5f));
        this.addActor(new Spine(world,226.64f,109.8f,3.72f,4.5f));
        this.addActor(new Spine(world,229.05f,67.1f,3.72f,4.5f));
        this.addActor(new Spine(world,233.55f,68.7f,3.72f,4.5f));
        this.addActor(new Spine(world,234.5f,73f,3.72f,4.5f));
        this.addActor(new Spine(world,230.79f,75.5f,3.72f,4.5f));
        this.addActor(new Spine(world,420.2f,107.5f,3.72f,4.5f));
        this.addActor(new Spine(world,423.2f,107.4f,3.72f,4.5f));
        this.addActor(new Spine(world,422.75f,103.3f,3.72f,4.5f));
        this.addActor(new Spine(world,416f,77.69f,3.72f,4.5f));
        this.addActor(new Spine(world,421.37f,77.93f,3.72f,4.5f));


        this.addActor(new Spine(world,498.25f,47f,3.72f,4.5f));
        this.addActor(new Spine(world,499.34f,45.97f,3.72f,4.5f));
        this.addActor(new Spine(world,504f,44.35f,3.72f,4.5f));
        this.addActor(new Spine(world,508.3f,45.54f,3.72f,4.5f));
        this.addActor(new Spine(world,511f,46.4f,3.72f,4.5f));


        float deviation = (float) 23.52;


        this.addActor(new Spine(world,499.34f+deviation,45.97f,3.72f,4.5f));
        this.addActor(new Spine(world,504f+deviation,43.7f,3.72f,4.5f));
        this.addActor(new Spine(world,508.3f+deviation,45.54f,3.72f,4.5f));
        this.addActor(new Spine(world,510.5f+deviation,46.4f,3.72f,4.5f));

        this.addActor(new Spine(world,632.81f,42.06f,4.64f,29.76f));

        this.addActor(new Spine(world,658.24f,50.33f,3.72f,4.5f));
        this.addActor(new Spine(world,659.32f,59.22f,3.72f,4.5f));

        this.addActor(new Spine(world,656.8f,52.96f,1.98f,5.5f));

        this.addActor(new Spine(world,775.1f,48.5f,3.72f,4.5f));
        this.addActor(new Spine(world,776.1f,51.9f,3.72f,4.5f));
        this.addActor(new Spine(world,777.71f,55.6f,3.72f,4.5f));
        this.addActor(new Spine(world,777.4f,59.25f,3.72f,4.5f));
        this.addActor(new Spine(world,775.5f,63.3f,3.72f,4.5f));
        this.addActor(new Spine(world,777.2f,67.5f,3.72f,4.5f));
        this.addActor(new Spine(world,777.78f,71.5f,3.72f,4.5f));
        this.addActor(new Spine(world,777.66f,75.71f,3.72f,4.5f));
        this.addActor(new Spine(world,776.19f,78.16f,3.72f,4.5f));
        this.addActor(new Spine(world,764.79f,90.94f,3.72f,4.5f));
        this.addActor(new Spine(world,759.09f,92.53f,3.72f,4.5f));
        this.addActor(new Spine(world,753.9f,85.67f,3.72f,4.5f));
        this.addActor(new Spine(world,753.73f,89.15f,3.72f,4.5f));
        this.addActor(new Spine(world,749.11f,92f,3.72f,4.5f));
        this.addActor(new Spine(world,745.45f,90.77f,3.72f,4.5f));
        this.addActor(new Spine(world,745,85.98f,3.72f,4.5f));
        this.addActor(new Spine(world,743.03f,82.39f,3.72f,4.5f));
        this.addActor(new Spine(world,743.87f,78.93f,3.72f,4.5f));
        this.addActor(new Spine(world,774.95f,75.82f,3.72f,4.5f));
        this.addActor(new Spine(world,774.41f,73.87f,3.72f,4.5f));
        this.addActor(new Spine(world,743.96f,78.16f,3.72f,4.5f));
        this.addActor(new Spine(world,745.4f,74.63f,3.72f,4.5f));
        this.addActor(new Spine(world,745.37f,70.9f,3.72f,4.5f));
        this.addActor(new Spine(world,746.12f,66.84f,3.72f,4.5f));
        this.addActor(new Spine(world,745.38f,63.14f,3.72f,4.5f));
        this.addActor(new Spine(world,774.66f,60.49f,3.72f,4.5f));
        this.addActor(new Spine(world,743.67f,56.71f,3.72f,4.5f));
        this.addActor(new Spine(world,745.14f,53.22f,3.72f,4.5f));


        this.addActor(new SleepingBear(world,97f,64f,20f,10f,ActConstants.BearID,"sleepingBear"));
        new BearAndMainCharacter();

        TongueMonster tongueMonster = new TongueMonster(world,AssetsLevel2.instance.daoju.bingkuai,AssetsLevel2.instance.decoration.csg1,205.6f,85.5f);
        this.addActor(tongueMonster);
        new TongueMonsterAndMainCharacter();


//AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animAttack,AssetsLevel2.instance.pingtaiguai.animBreath
        new EatPlatformAndMainCharacter();
        EatPlatform eatPlatform1 = new EatPlatform(world,225.75f,90, AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animAttack,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animBreath);
        this.addActor(eatPlatform1);

        EatPlatform eatPlatform2 = new EatPlatform(world,235.85f,94.5f,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animAttack,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animBreath);
        this.addActor(eatPlatform2);

        EatPlatform eatPlatform3 = new EatPlatform(world,231.38f,99.5f,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animAttack,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animBreath);
        this.addActor(eatPlatform3);

        EatPlatform eatPlatform4 = new EatPlatform(world,238.5f,104.5f,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animAttack,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animBreath);
        this.addActor(eatPlatform4);

        EatPlatform eatPlatform5 = new EatPlatform(world,238f,110.5f,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animAttack,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animBreath);
        this.addActor(eatPlatform5);

        EatPlatform eatPlatform6 = new EatPlatform(world,228f,114f,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animAttack,AssetsLevel2.instance.pingtaiguai.animBreath,AssetsLevel2.instance.pingtaiguai.animBreath);
        this.addActor(eatPlatform6);


        BallLauncher ballLauncher = new BallLauncher(world,AssetsLevel2.instance.decoration.huolu,158.3f,41);
        this.addActor(ballLauncher);
        new BallLauncherAndMainCharacter();


        BallReceiver ballReceiver = new BallReceiver(world,AssetsLevel2.instance.decoration.huoqiujieshouqi,207.3f,36f);
        this.addActor(ballReceiver);
        new BallAndBallReceiver();


        this.addActor(new Ice(151,70,ActConstants.IceID,world,"ice"));
        new BridgeAndIce();
        this.addActor(new brokenBridge( AssetsLevel2.instance.daoju.bingtai,AssetsLevel2.instance.daoju.bingtai, 157, 56, 3f,2f,ActConstants.brokenBridgeID, world, "brokenIce"));

        new ReflectiveStoneAndBullet();
        this.addActor(new StillReflectiveStone(650*50, (int) (59.8*50),ActConstants.stillReflectiveStoneID,world,1));
        this.addActor(new laserTransmitter((int) (655.85*50), 49*50, ActConstants.laserTransmitterID, world, "laserTransmitter"));
        this.addActor(new StillReflectiveStone(631*50, (int) (67.5*50),ActConstants.stillReflectiveStoneID,world,2));
        this.addActor(new StillReflectiveStone(656*50, (int) (73.3*50),ActConstants.stillReflectiveStoneID,world,1));
        this.addActor(new StillReflectiveStone(617*50, (int) (81.7*50),ActConstants.stillReflectiveStoneID,world,1));

        new ReflectiveStoneAndMain();
        new StillReflectiveStoneAndMain();



        this.addActor(new MoveLauncher(world,490.1f,77));
        new MoveLauncherAndBullet();




        //244.04076,78.68498
//357.14578,99.2157
        this.addActor(new Portal(12200,3900,357,99,ActConstants.portalID,world,"PortalIceTriggered",false,"none"));


//Portal的位置：436，80
//Dst:515,49
        this.addActor(new Portal(21800,4000,515,49,ActConstants.portalID,world,"Portal2",false,"none"));
        this.addActor(new rotateSwitch(AssetsUI.instance.jiguan.jg0, AssetsUI.instance.jiguan.jg1, 422f, 115f, ActConstants.switchID, world,"rotateSwitchPortal", "portal"));
        this.addActor(new Portal(27050,2400,604,51,ActConstants.portalID,world,"Portal3",true,"none"));
        this.addActor(new Portal(30000,4000,537,66,ActConstants.portalID,world,"Portal4",true,"none"));
        this.addActor(new Portal(24300,2435,771,42,ActConstants.portalID,world,"Portal5",true,"none"));
        this.addActor(new Portal(38950,4100,485,65,ActConstants.portalID,world,"PortalFinal",true,"none"));


        this.addActor(new LaserDoor(world,610,83.5f));
        new LaserDoorAndLaser();


        Blower blower = new Blower(world,155.6f,48.7f,1.5f,15,AssetsLevel2.instance.decoration.animCf);
        this.addActor(blower);

        this.addActor(new SandPlat(AssetsLevel2.instance.decoration.sand,Assets.instance.bunny.getAnimCopterRotate,645f,51f,ActConstants.SandID,world,"sandPlat1"));
        new SandAndMainCharacter();
//mousepoint   645.78735   53.93447
//        639.74365,71.87999
//        385.1166,94.39422  diyige futukui
//        243.1743,78.68498

        this.addActor(new SandPlat(AssetsLevel2.instance.decoration.sand,Assets.instance.bunny.getAnimCopterRotate,639.7f,69.9f,ActConstants.SandID,world,"sandPlat2"));
//385.9601,91.62201
        this.addActor(new SandPlat(AssetsLevel2.instance.decoration.sand,Assets.instance.bunny.getAnimCopterRotate,385.9f,89.6f,ActConstants.SandID,world,"sandPlat3"));

        new BlowerAndMainCharacter();
//yzh***************************************************

//

//
//        new IceFairyAndMainCharacter();
//
//        this.addActor(new Fairy(2, Assets.instance.mainCharacter.animBreath,Assets.instance.bunny.getAnimCopterRotate,300,500,ActConstants.iceFairyID,world,"IceFairy"));
//
//        this.addActor(new Spine(world,60,10,1,3));
//        new MainCharacterAndSpine();
//
//
//        this.addActor(new Boss1(world,50,8));
//        new Boss1SensorContact();
//        new Boss1AndMainCharacter();
//
//
//        this.addActor(new Axe(world,40,15));
//        new AxeAndMainCharacter();
//        new AxeSensorContact();
//
//        this.addActor(new Frag(world,30,8));
//
//        new HeatFragEye();
//
//
//        new BulletAndMonsterA();
//        MonsterA monsterA = new MonsterA(world,20,10,Assets.instance.bunny.animCopterTransform,Assets.instance.bunny.animCopterTransformBack,Assets.instance.bunny.getAnimCopterRotate,Assets.instance.mainCharacter.animBreath,Assets.instance.mainCharacter.animRun,Assets.instance.goldCoin.animGoldCoin);
//        this.addActor(monsterA);
//
//
//        MonsterA monsterA2 = new MonsterA(world,25,10,Assets.instance.bunny.animCopterTransform,Assets.instance.bunny.animCopterTransformBack,Assets.instance.bunny.getAnimCopterRotate,Assets.instance.mainCharacter.animBreath,Assets.instance.mainCharacter.animRun,Assets.instance.goldCoin.animGoldCoin);
//        this.addActor(monsterA2);
//
//        new MonsterASensorAndMainCharacter();
//
//        Flower flower = new Flower(world,10,10,Assets.instance.bunny.getAnimCopterRotate,Assets.instance.mainCharacter.animRun,Assets.instance.mainCharacter.animRun);
//        this.addActor(flower);
//        new FlowerAndMainCharacter();
//
//
//        EatPlatform eatPlatform1 = new EatPlatform(world,70,10,Assets.instance.mainCharacter.animRun,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animBreath,Assets.instance.goldCoin.animGoldCoin);
//        this.addActor(eatPlatform1);
//
//        EatPlatform eatPlatform2 = new EatPlatform(world,80,10,Assets.instance.mainCharacter.animRun,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animBreath,Assets.instance.goldCoin.animGoldCoin);
//        this.addActor(eatPlatform2);
//
//
//        new EatPlatformAndMainCharacter();
//
//
//        TongueMonster tongueMonster = new TongueMonster(world,Assets.instance.bunny.animCopterTransform,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animBreath,Assets.instance.mainCharacter.animRun,85,13);
//        this.addActor(tongueMonster);
//
//
//        TongueMonster tongueMonster2 = new TongueMonster(world,Assets.instance.bunny.animCopterTransform,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animBreath,Assets.instance.mainCharacter.animRun,90,13);
//        this.addActor(tongueMonster2);
//
//        new TongueMonsterAndMainCharacter();
//
//
//        Blower blower = new Blower(world,6,10,3,10,Assets.instance.bunny.animNormal);
//        this.addActor(blower);
//
//        new BlowerAndMainCharacter();
//
//
//
//
//
//
//        this.addActor(new BossLauncher(world,45,15));
//        new BossLauncherAndMainCharacter();
//        ((BossLauncher)ActConstants.publicInformation.get("BossLauncher")).start();
//
////
////        new BulletAndMonsterA();
////        MonsterA monsterA2 = new MonsterA(world,25,10,Assets.instance.bunny.animCopterTransform,Assets.instance.bunny.animCopterTransformBack,Assets.instance.bunny.getAnimCopterRotate,Assets.instance.mainCharacter.animBreath,Assets.instance.mainCharacter.animRun,Assets.instance.goldCoin.animGoldCoin);
////        this.addActor(monsterA2);
//
//
//
//        BallLauncher ballLauncher = new BallLauncher(world,Assets.instance.bunny.animCopterTransform,Assets.instance.bunny.animNormal,20,10);
//        this.addActor(ballLauncher);
//        new BallLauncherAndMainCharacter();
//
//        BallReceiver ballReceiver = new BallReceiver(world,Assets.instance.bunny.animCopterTransform,Assets.instance.bunny.animNormal,25,10);
//        this.addActor(ballReceiver);
//        new BallAndBallReceiver();


//
//        this.addActor(new ReverberateAxe(world,40,15));
//        new AxeAndMainCharacter();
//        new AxeSensorContact();

//yzh***************************************************************


//        //************************************郐洋
//        new BridgeAndMainCharacter();
//        new PortalAndMainCharacter();
//        new BridgeAndIce();
//        new SensorAndMainCharacter();
//        new ReflectiveStoneAndBullet();
//        new SandAndMainCharacter();
//        this.addActor(new rotateSwitch( Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 6580, 82, ActConstants.switchID, world, "rotateSwitch", "door"));
//        this.addActor(new Door(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 6980, 82, ActConstants.switchID, world, "door"));
//        this.addActor(new Ice(5600,650,ActConstants.IceID,world,"ice"));
//        this.addActor(new brokenBridge( Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 500, 4500, ActConstants.brokenBridgeID, world, "brokenBridge"));
//        this.addActor(new laserTransmitter(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate,7500, 82, ActConstants.laserTransmitterID, world, "laserTransmitter"));
//        this.addActor(new Sensor(7300,82,ActConstants.SensorID,world,"Sensor"));
//        this.addActor(new ReflectiveStone(8080,82,ActConstants.ReflectiveStoneID,world,"ReflectiveStone1"));
//        this.addActor(new SandPlat( Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 9033, 585, ActConstants.SandID, world, "SandPlat1"));
//        this.addActor(new Portal(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate,8500,82,7500,82,ActConstants.portalID,world,"Portal",true,"Stage1"));
//
        ActConstants.publicInformation.put("stage3", this);
//        //************************************郐洋



    }

    @Override
    public void act() {
        super.act();

        world.step(1.0f/60.0f, 6,6);
        //上面这个要放在各个stage里，


        if(ActConstants.physicalActionList.size()!=0){
            for(int i = 0; i<ActConstants.physicalActionList.size(); i++){
               ActConstants.physicalActionList.get(i).act();
                ActConstants.physicalActionList.remove(i);
            }
        }


        cameraFocus.innerBoundary(2,2,19,8);//

        //
        cameraPhysic.update();

        //
        cameraGUI.update();

    }

    @Override
    public void draw() {


        //
        orthogonalTiledMapRenderer.setView(this.stageCamera);
//        orthogonalTiledMapRenderer.render();

        orthogonalTiledMapRenderer.getBatch().begin();
        orthogonalTiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("L1"));
        orthogonalTiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("L2"));




        super.draw();//


        orthogonalTiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("L3"));
        orthogonalTiledMapRenderer.getBatch().end();


        AssetsUI.instance.updateSprite();
//        renderGui(batch);
    }
    public void renderGui(SpriteBatch batch){
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();

        AssetsUI.instance.drawUpdate(batch);
        batch.end();
    }
    public World getWorld(){
        return world;
    }
}
