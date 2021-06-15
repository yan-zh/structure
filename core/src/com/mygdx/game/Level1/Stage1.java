package com.mygdx.game.Level1;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.NormalActors.Boss1;
import com.mygdx.game.Level1.NormalActors.ChangeCamera;
import com.mygdx.game.Level2.ContactReactions.*;
import com.mygdx.game.Level2.NormalActors.*;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.Listeners.PhysicalContactListener;
import com.mygdx.game.Listeners.UserInputListener;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.CameraFocus;
import com.mygdx.game.Tools.LoadTiledMap;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.Fairy;
import com.mygdx.game.abstraction.MyStage;

public class Stage1 extends MyStage {


    Box2DDebugRenderer boxRender;//物理世界绘制器
    OrthographicCamera cameraPhysic;//物理世界相机
    OrthographicCamera stageCamera;//舞台用的摄像机

    CameraFocus cameraFocus;


    TiledMap tiledMap;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;

//    SpriteBatch spriteBatch;

    InputMultiplexer inputMultiplexer;

    public int x;

    // GUI界面的相机
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;





    ChangeCamera changeCamera;

    public Stage1(InputMultiplexer inputMultiplexer) {



        AssetsLevel1.instance.instance.init(new AssetManager());

        ActConstants.changeStageTo = 0;

        cUp=740;
        cDown=740;
        cleft=960;
        cright=2000000;
        this.inputMultiplexer = inputMultiplexer;
        x=1;

//        spriteBatch = new SpriteBatch();

        this.inputMultiplexer.addProcessor(this);//加入监听,接受来自最外层的信息。最外层的用户动作信息通过这个分配到各个stage

        //一个舞台代表游戏的一个关，每关各自使用一个物理世界
        world = new World(new Vector2(0,-10),true);
        world.setContactListener(new PhysicalContactListener());
        //这个类包含一些用于定义物理实体属性的函数，一般的物理实体都可以通过这个类定义
        PhysicalEntityDefine.boundWorld(world);

        //stage2的第一个演员，如果这个演员的某些函数需要在其他类的实体中被调用，可以选择把它的引用放在ActConstants里
        //添加常规演员，是关卡一开始就有的演员。子弹之类的临时的或在某些特定条件下出现的演员在监听函数里添加
        this.addActor(new MainCharacter(world,27,6f));//单位是米  27,6f  496 5
        this.addActor(new Beacon(AssetsLevel1.instance.zj.animBreath, AssetsLevel1.instance.zj.animAttack, 27, 6, ActConstants.beaconID, world, "Beacon"));

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


        changeCamera = new ChangeCamera(cameraPhysic,stageCamera);
        ActConstants.publicInformation.put("changeCamera",changeCamera);


        //添加添加自定义的用户输入监听
        this.addListener(new UserInputListener());//这个监听是对从最外层传入的输入信息的响应


        //根据读取tiledmap。生成地图的背景和基础物理实体（包括不动的，比如石头，墙什么的）
        //tiledMap = new TmxMapLoader().load("core/assets/bing/bing.tmx");
        tiledMap = new TmxMapLoader().load("core/assets/JX/JX.tmx");
        //绘制tiledmap提供的背景用的类
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        //根据tiledmap中的object图层自动在物理世界中创建实体
        LoadTiledMap.doScene2(world,this,tiledMap);

        //这个是因为图本身很大，有些部分是透明的，所以看起来位置不对

        //调整两个个相机到起始位置
        cameraFocus.directFocusOn(0,10.8f);//这里单位是米

        //把stage1加入公共域，用于之后的析构和交互
        ActConstants.publicInformation.replace("CurrentStage",this);

        //为主角添加一个技能，具体有关添加技能的细节在这个类里会写
        //ActConstants.skillGroups[1]=new SkillGourpFire();

        //为这一关用到的物理碰撞监听添加一个函数



        // 测试GUI部分：史用
// 初始化batch
        batch = new SpriteBatch();
// 创建GUI相机并配置参数
        cameraGUI = new OrthographicCamera(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);
        cameraGUI.position.set(0,0,0);
        cameraGUI.setToOrtho(true);
// 反转y轴
        cameraGUI.update();



        //*********************   public listen
        new BulletAndMonsterA();
        new MonsterASensorAndMainCharacter();

        new BulletDispose();
        new BulletAndMain();

        new GroundAndMainCharacter();

        new PortalAndMainCharacter();


        //*********************  actors
        //region1
        this.addActor(new Fairy(1, AssetsLevel1.instance.fijling.animBreath,AssetsLevel1.instance.fijling.animAttack, (int) (44.5*50), (int) (14*50),ActConstants.windFairyID,world,"WindFairy"));
        new WindFairyAndMainCharacter();

        MonsterA monsterA = new MonsterA(world,123.6f,2.2f,1,AssetsLevel1.instance.cdxgw.animBreath,AssetsLevel1.instance.cdxgw.animDead);
        this.addActor(monsterA);

        this.addActor(new Portal(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate,5690,104,239,5,ActConstants.portalID,world,"Portal1",true,"none"));


        //Region two
        this.addActor(new brokenBridge( Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 286, 3,5f,0.26f, ActConstants.brokenBridgeID, world, "brokenBridge"));
        new BridgeAndMainCharacter();

        MonsterA monsterA2 = new MonsterA(world,350f,13f,1,AssetsLevel1.instance.cddpxgw.animBreath,AssetsLevel1.instance.cddpxgw.animDead);
        this.addActor(monsterA2);

        this.addActor(new Portal(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate,18150,600,505,3,ActConstants.portalID,world,"Portal2",true,"none"));


        //Region3
        this.addActor(new Boss1(world,499,13));
        new Boss1SensorContact();
        new Boss1AndMainCharacter();
        this.addActor(new Portal(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate,37860,148,505,3,ActConstants.portalID,world,"Portal3",true,"Stage2"));
//        this.addActor(new Portal(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate,47*50, (int) (6.2*50),505,3,ActConstants.portalID,world,"Portal3",true,"Stage2"));


        cameraFocus.directFocusOn(27,9.5f);
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




//        cameraFocus.innerBoundary(2,2,19,8);//进行物理相机和舞台相机的调整，在屏幕中划出一个区域作为触发相机调整的边框
        cameraFocus.LRBoundary(2,2,19,8);

//应用相机位置更新
        cameraPhysic.update();//主角内存图片的相机就不用了，应为舞台自己有一个相机，舞台内新做的内存机替换了已有的，所以stage类每次会自动调用舞台新作的内存相机的update


//        changeCamera.act();**************************这句一直需要

        //测试地相机远近调整用的
//        x++;
//        if(x>=600){
//            changeCamera.close();
//            x=1;
//        }else if(x>=300){
//            changeCamera.leave();
//        }


        // 应用GUI相机的位置更新
        cameraGUI.update();

    }

    @Override
    public void draw() {



        //绘制tiledmap的背景图
        orthogonalTiledMapRenderer.setView(this.stageCamera);


//        orthogonalTiledMapRenderer.getBatch().begin();
//        orthogonalTiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("L2"));
        orthogonalTiledMapRenderer.render();


       // System.out.println(tiledMap.getLayers().get("L2")==null);
//        orthogonalTiledMapRenderer.render();

        //绘制物理实体
        boxRender.render(world, cameraPhysic.combined);//结合相机进行绘制

        //不需要主动写代码绘制舞台相机，舞台相机是自动更新并绘制的

        super.draw();//这个就是依次调用actor的draw
//        orthogonalTiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("L1"));
//        orthogonalTiledMapRenderer.getBatch().end();
//        if(ActConstants.isChange) {
//            this.dispose();
//            MyGdxGame.currentStage = new Stage2(ActConstants.inputMultiplexer);
//        }


        // 尝试在此处绘制GUI图片
        renderGui(batch);
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void dispose() {

        super.dispose();
    }

    public void renderGui(SpriteBatch batch){
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        // 绘制人物的状态栏（左上角）
        AssetsUI.instance.drawUpdate(batch);
        batch.end();

    }
}
