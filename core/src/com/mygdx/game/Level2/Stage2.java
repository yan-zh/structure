package com.mygdx.game.Level2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.ContactReactions.*;
import com.mygdx.game.Level2.NormalActors.*;
import com.mygdx.game.Listeners.PhysicalContactListener;
import com.mygdx.game.Listeners.UserInputListener;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.CameraFocus;
import com.mygdx.game.Tools.LoadTiledMap;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.Fairy;
import com.mygdx.game.abstraction.MyStage;
import jdk.tools.jaotc.Main;

public class Stage2 extends MyStage {


    Box2DDebugRenderer boxRender;//物理世界绘制器
    OrthographicCamera cameraPhysic;//物理世界相机
    OrthographicCamera stageCamera;//舞台用的摄像机

    CameraFocus cameraFocus;


    TiledMap tiledMap;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;


    public Stage2(InputMultiplexer inputMultiplexer){
        //注意如果把某些类的实体加入到ActConstants中，需要看一下使用的顺序，有时候先使用了ActConstants中的对象，但是这个对象是在后面才被加入的，可能会空指针异常

        inputMultiplexer.addProcessor(this);//加入监听,接受来自最外层的信息。最外层的用户动作信息通过这个分配到各个stage

        //一个舞台代表游戏的一个关，每关各自使用一个物理世界
        world = new World(new Vector2(0,-10),true);
        world.setContactListener(new PhysicalContactListener());
        //这个类包含一些用于定义物理实体属性的函数，一般的物理实体都可以通过这个类定义
        PhysicalEntityDefine.boundWorld(world);

        //stage2的第一个演员，如果这个演员的某些函数需要在其他类的实体中被调用，可以选择把它的引用放在ActConstants里
        //添加常规演员，是关卡一开始就有的演员。子弹之类的临时的或在某些特定条件下出现的演员在监听函数里添加
        this.addActor(new MainCharacter(world,59.9f,84f));//单位是米

        //每个舞台自己准备摄像机
        boxRender = new Box2DDebugRenderer();//物理实体绘制器，用于绘制物理实体形状
        cameraPhysic = new OrthographicCamera();//用于投影物理实体形状的摄像机
        //绑定物理绘制器和相机
        cameraPhysic.setToOrtho(false, (ActConstants.SCREEN_WIDTH)/ ActConstants.worldSize_pAndPhysic,(ActConstants.SCREEN_HEIGHT)/ ActConstants.worldSize_pAndPhysic);//这个是摄像机的框的大小，摄像机取景后还要经过最外层界面的压缩得到最终效果
        //用户绘制一般画面的相机
        stageCamera = (OrthographicCamera) this.getCamera();
        stageCamera.setToOrtho(false, ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);

        //cameraFocus是用于使相机跟踪主角的类
        cameraFocus = new CameraFocus(cameraPhysic, stageCamera);




        //添加添加自定义的用户输入监听
        this.addListener(new UserInputListener());//这个监听是对从最外层传入的输入信息的响应


        //根据读取tiledmap。生成地图的背景和基础物理实体（包括不动的，比如石头，墙什么的）
        tiledMap = new TmxMapLoader().load("core/assets/woodMap/JX01.tmx");
        //绘制tiledmap提供的背景用的类
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //根据tiledmap中的object图层自动在物理世界中创建实体
        LoadTiledMap.doScene2(world,this,tiledMap);

        //这个是因为图本身很大，有些部分是透明的，所以看起来位置不对

        //调整两个个相机到起始位置
        cameraFocus.focusOn(0,5,1);//这里单位是米

        //把stage1加入公共域，用于之后的析构和交互
        ActConstants.publicInformation.replace("CurrentStage",this);

        //为主角添加一个技能，具体有关添加技能的细节在这个类里会写
        //ActConstants.skillGroups[1]=new SkillGourpFire();

        //为这一关用到的物理碰撞监听添加一个函数



//yzh***************************************************
        new GroundAndMainCharacter();
        new MainCharacterAndSpine();
        new BridgeAndMainCharacter();


        //木系荆棘 * 区域1
        this.addActor(new Spine(world,8.5f,84.5f,5,0.5f));
        this.addActor(new Spine(world,14f,82.5f,0.5f,11f));
        this.addActor(new Spine(world,20.5f,85f,5.5f,3f));
        this.addActor(new Spine(world,26.5f,82.5f,0.5f,10f));
        this.addActor(new Spine(world,50.5f,82.5f,0.5f,16f));
        this.addActor(new Spine(world,71.2f,82.3f,0.5f,3.5f));
        //缺一个lyq的平台和可以上下的桥@lyq


        //区域2
        this.addActor(new Spine(world,101.4f,84.6f,5f,0.5f));
        this.addActor(new Spine(world,120f,86f,0.5f,12f));
        this.addActor(new Spine(world,125.5f,89f,0.5f,1f));
        this.addActor(new Spine(world,122.5f,89f,0.5f,1f));
        this.addActor(new brokenBridge( Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 8500, 5150, ActConstants.brokenBridgeID, world, "brokenDoor"));





//        new BulletDispose();
//
//        //风精灵
//        new WindFairyAndMainCharacter();
//
//        this.addActor(new Fairy(1, Assets.instance.mainCharacter.animBreath,Assets.instance.bunny.getAnimCopterRotate,300,500,ActConstants.windFairyID,world,"WindFairy"));
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
//        new BulletAndMonsterA();
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
//        ActConstants.publicInformation.put("stage2", this);
//        //************************************郐洋


        //****************************林韫奇 主角9600
        this.addActor(new Platform(world,25,88,3f,5f,ActConstants.platformID,"Platform"));
        this.addActor(new Platform(world,84,80,3f,5f,ActConstants.platformID,"Platform"));
        this.addActor(new ThinSurface(world,54.1f,65,6.3f,0.5f,
                ActConstants.thinSurfaceID,"thinSurface"));
        this.addActor(new HangedBalls(world,185,83,"012",ActConstants.hangedBallsID,"hangedBalls"));
        this.addActor(new PswDoor(world,64f,82f,11f,1f,ActConstants.PswDoorID,"pswDoor"));
        this.addActor(new SleepingBear(world,212,10,5f,3f,ActConstants.BearID,"sleepingBear"));
        this.addActor(new Bubbles(world,216,10,15,0.7f,ActConstants.BubbleID,"bubbles"));
        new ThinSurfaceContact();
        new BallsContact();
        new BearWindContact();
        new PlatformAndMainCharacter();
        new BearAndMainCharacter();
        new BubbleAndCharacter();
        new PswDoorContact();
    }

    @Override
    public void act() {
        super.act();

        world.step(1.0f/60.0f, 6,6);//进行一次物理世界模拟，第一个 时间步，和系统时间同步；速度和位置的模拟精度，越大越准
        //上面这个要放在各个stage里，因为不到这个stage运行的时候stage对应的物理世界是不动的


        if(ActConstants.physicalActionList.size()!=0){
            for(int i = 0; i<ActConstants.physicalActionList.size(); i++){
               ActConstants.physicalActionList.get(i).act();
                ActConstants.physicalActionList.remove(i);
            }
        }


        cameraFocus.innerBoundary(2,2,19,8);//进行物理相机和舞台相机的调整，在屏幕中划出一个区域作为触发相机调整的边框

        //应用相机位置更新
        cameraPhysic.update();//主角内存图片的相机就不用了，应为舞台自己有一个相机，舞台内新做的内存机替换了已有的，所以stage类每次会自动调用舞台新作的内存相机的update



    }

    @Override
    public void draw() {


        //绘制tiledmap的背景图
        orthogonalTiledMapRenderer.setView(this.stageCamera);
        orthogonalTiledMapRenderer.render();

        //绘制物理实体
        boxRender.render(world, cameraPhysic.combined);//结合相机进行绘制

        //不需要主动写代码绘制舞台相机，舞台相机是自动更新并绘制的

        super.draw();//这个就是依次调用actor的draw
    }

    public World getWorld(){
        return world;
    }
}
