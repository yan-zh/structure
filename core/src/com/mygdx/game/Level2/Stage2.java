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
import com.mygdx.game.Tools.asset.AssetsLevel1;
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

        ActConstants.changeStageTo = 0;

        cUp=300000;
        cDown=-300000;
        cleft=-300000;
        cright=300000;



        //注意如果把某些类的实体加入到ActConstants中，需要看一下使用的顺序，有时候先使用了ActConstants中的对象，但是这个对象是在后面才被加入的，可能会空指针异常

        System.out.println("新建了一个Stage2");
        inputMultiplexer.addProcessor(this);//加入监听,接受来自最外层的信息。最外层的用户动作信息通过这个分配到各个stage

        //一个舞台代表游戏的一个关，每关各自使用一个物理世界
        world = new World(new Vector2(0,-10),true);
        world.setContactListener(new PhysicalContactListener());
        //这个类包含一些用于定义物理实体属性的函数，一般的物理实体都可以通过这个类定义
        PhysicalEntityDefine.boundWorld(world);

        //stage2的第一个演员，如果这个演员的某些函数需要在其他类的实体中被调用，可以选择把它的引用放在ActConstants里
        //添加常规演员，是关卡一开始就有的演员。子弹之类的临时的或在某些特定条件下出现的演员在监听函数里添加
        this.addActor(new MainCharacter(world,66,14));//单位是米 7 89初始位置 7 66  105 11
        this.addActor(new Beacon(AssetsLevel1.instance.zj.animBreath, AssetsLevel1.instance.zj.animAttack, 7, 66, ActConstants.beaconID, world, "Beacon"));

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
//        tiledMap = new TmxMapLoader().load("core/assets/JX03/JX03.tmx");
        tiledMap = new TmxMapLoader().load("core/assets/SL/SL01.tmx");
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



        //测试***********************************
//        this.addActor(new Fairy(3, Assets.instance.mainCharacter.animBreath,Assets.instance.bunny.getAnimCopterRotate, (int) (29.89*50),11*50,ActConstants.woodFairyID,world,"WoodFairy"));
        //this.addActor(new Fairy(3, Assets.instance.mainCharacter.animBreath,Assets.instance.bunny.getAnimCopterRotate, (int) (8*50),70*50,ActConstants.woodFairyID,world,"WoodFairy"));

        new SandFairyAndMainCharacter();
        new WoodFairyAndMainCharacter();
        new BulletAndMonsterA();
        new MonsterASensorAndMainCharacter();
        new WindFairyAndMainCharacter();
        new PortalAndMainCharacter();

        //测试***********************************



//yzh***************************************************
        new BulletDispose();
        new GroundAndMainCharacter();
        new MainCharacterAndSpine();
        new BridgeAndMainCharacter();
        new BulletAndMain();


        //荆棘》》》》》》》》》》》》》》》》》》》》》
        this.addActor(new Spine(world,10.4f,59.34f,3.72f,4.5f));
        this.addActor(new Spine(world,14.98f,58.59f,3.72f,4.5f));
        this.addActor(new Spine(world,17.98f,61.2f,3.72f,4.5f));
        this.addActor(new Spine(world,18.9f,64f,3.72f,4.5f));
        this.addActor(new Spine(world,20.04f,65.13f,3.72f,4.5f));
        this.addActor(new Spine(world,20.2f,62.18f,3.72f,4.5f));

        this.addActor(new Spine(world,26.45f,59.09f,3.72f,4.5f));
        this.addActor(new Spine(world,45.81f,58.82f,3.72f,4.5f));
        this.addActor(new Spine(world,50.41f,59.17f,3.72f,4.5f));
        this.addActor(new Spine(world,54.72f,58.83f,3.72f,4.5f));




        this.addActor(new Spine(world,68.17f,59.12f,2f,2f));
        this.addActor(new Spine(world,69.3f,60.32f,2f,2f));
        this.addActor(new Spine(world,70.04f,59.45f,2f,2f));
        this.addActor(new Spine(world,101.18f,63.77f,2f,2f));
        this.addActor(new Spine(world,101.27f,61f,2f,2f));
        this.addActor(new Spine(world,102.62f,60.2f,2f,2f));
        this.addActor(new Spine(world,110.65f,62.15f,2f,2f));
        this.addActor(new Spine(world,112.37f,61.74f,2f,2f));
        this.addActor(new Spine(world,114.17f,61.36f,2f,2f));
        this.addActor(new Spine(world,116.05f,61.12f,2f,2f));

        this.addActor(new Spine(world,118.15f,61.05f,2f,2f));
        this.addActor(new Spine(world,120.15f,61.40f,2f,2f));


        this.addActor(new Spine(world,122.25f,62f,2f,2f));
        this.addActor(new Spine(world,124.6f,62.29f,2f,2f));
        this.addActor(new Spine(world,125.62f,63f,2f,2f));
        this.addActor(new Spine(world,126.2f,65.63f,2f,2f));

        this.addActor(new Spine(world,66.11f,51.74f,2f,2f));
        this.addActor(new Spine(world,63.85f,49.93f,2f,2f));
        this.addActor(new Spine(world,61.39f,48.02f,2f,2f));
        this.addActor(new Spine(world,60.45f,47.51f,2f,2f));
        this.addActor(new Spine(world,57.95f,45.78f,2f,2f));
        this.addActor(new Spine(world,56.86f,43.94f,2f,2f));
        this.addActor(new Spine(world,55.64f,42.62f,2f,2f));
        this.addActor(new Spine(world,54.49f,41.52f,2f,2f));


        this.addActor(new brokenBridge( Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 8500-60, 5150-1170, 3f,2f,ActConstants.brokenBridgeID, world, "brokenDoor"));
        this.addActor(new rotateSwitch(Assets.instance.goldCoin.animGoldCoin, Assets.instance.bunny.getAnimCopterRotate, 94.8f-1.2f, 49.4f-23.4f, ActConstants.switchID, world,"rotateSwitchFrag", "frag"));



        Flower flower = new Flower(world,64-1.2f,13.4f,AssetsLevel1.instance.srh.animBreath,AssetsLevel1.instance.srh.animDead,AssetsLevel1.instance.srh.animDead);
        this.addActor(flower);
        new FlowerAndMainCharacter();
        //配给检测食人花的
        this.addActor(new Fairy(1, Assets.instance.mainCharacter.animBreath,Assets.instance.bunny.getAnimCopterRotate, (int) (66*50),14*50,ActConstants.windFairyID,world,"WindFairy"));



        this.addActor(new ReverberateAxe(world,86.5f-1.2f,45.5f-23.4f));
        new AxeAndMainCharacter();
        new AxeSensorContact();


        this.addActor(new Frag(world,142-1.2f,32-23.4f));
        new HeatFragEye();
        this.addActor(new Frag(world,147-1.2f,45-23.4f));
        this.addActor(new Frag(world,165-1.2f,65-23.4f));

        this.addActor(new rotateSwitch(Assets.instance.goldCoin.animGoldCoin, Assets.instance.bunny.getAnimCopterRotate, 183f-1.2f, 64f-23.4f, ActConstants.switchID, world,"rotateSwitchDoor", "door"));

        this.addActor(new Door(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 193f-1.2f, 39.5f-23.4f,1f/ ActConstants.worldSize_shapeAndPhysics, 7f/ ActConstants.worldSize_shapeAndPhysics, ActConstants.switchID, world, "door"));

        this.addActor(new rotateSwitch(Assets.instance.goldCoin.animGoldCoin, Assets.instance.bunny.getAnimCopterRotate, 94.8f-1.2f, 49.4f-23.4f, ActConstants.switchID, world,"rotateSwitchFrag", "frag"));






        ActConstants.publicInformation.put("stage2", this);


        this.addActor(new Platform(world,27-1.2f,88-23.4f,3f,5f,ActConstants.platformID,"Platform"));
        this.addActor(new Platform(world,84-1.2f,80-23.4f,3f,5f,ActConstants.platformID,"Platform"));


        this.addActor(new ThinSurface(world,54.1f-1.2f,65-23.4f,6.3f,0.5f,
                ActConstants.thinSurfaceID,"thinSurface"));
        this.addActor(new HangedBalls(world,203-1.2f,65,"10210",ActConstants.hangedBallsID,"hangedBalls"));
        this.addActor(new SleepingBear(world,212-1.2f,10-23.4f,5f,3f,ActConstants.BearID,"sleepingBear"));
//        this.addActor(new Bubbles(world,216-1.2f,10-23.4f,15,0.7f,ActConstants.BubbleID,"bubbles"));
        new ThinSurfaceContact();
        new BallsContact();
        new BearWindContact();
        new PlatformAndMainCharacter();
        new BearAndMainCharacter();
        new BubbleAndCharacter();
        this.addActor(new PswDoor(world,64f-1.2f,82f-23.4f,11f,1f,ActConstants.PswDoorID,"pswDoor"));
        new PswDoorContact();
        this.addActor(new Portal(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate,10750,2800,505,3,ActConstants.portalID,world,"Portal1",true,"Stage3"));

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
