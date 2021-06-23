package com.mygdx.game.Level1;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.ContactReactions.*;
import com.mygdx.game.Level1.NormalActors.Boss1;
import com.mygdx.game.Level1.NormalActors.ChangeCamera;
import com.mygdx.game.Level1.NormalActors.MonsterA;
import com.mygdx.game.Level2.NormalActors.*;
import com.mygdx.game.PublicContactReaction.*;
import com.mygdx.game.Listeners.PhysicalContactListener;
import com.mygdx.game.Listeners.UserInputListener;
import com.mygdx.game.Tools.*;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.*;

public class Stage1 extends MyStage {


    Box2DDebugRenderer boxRender;
    OrthographicCamera cameraPhysic;
    OrthographicCamera stageCamera;

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



        AssetsLevel0.instance.init(new AssetManager());
        AssetsLevel1.instance.init(new AssetManager());
//        AssetsLevel2.instance.init(new AssetManager());

        ActConstants.changeStageTo = 0;

        cUp=740;
        cDown=740;
        cleft=960;
        cright=2000000;
        this.inputMultiplexer = inputMultiplexer;
        x=1;



        this.inputMultiplexer.addProcessor(this);


        world = new World(new Vector2(0,-10),true);
        world.setContactListener(new PhysicalContactListener());

        //used to define entity in this world
        PhysicalEntityDefine.boundWorld(world);


        this.addActor(new MainCharacter(world,27,6f));
        this.addActor(new Beacon( 27, 6, ActConstants.beaconID, world, "Beacon"));


        boxRender = new Box2DDebugRenderer();
        cameraPhysic = new OrthographicCamera();

        cameraPhysic.setToOrtho(false, (ActConstants.SCREEN_WIDTH)/ ActConstants.worldSize_pAndPhysic,(ActConstants.SCREEN_HEIGHT)/ ActConstants.worldSize_pAndPhysic);//这个是摄像机的框的大小，摄像机取景后还要经过最外层界面的压缩得到最终效果
        //camera for actor
        stageCamera = (OrthographicCamera) this.getCamera();
        stageCamera.setToOrtho(false, ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);


        cameraFocus = new CameraFocus(cameraPhysic, stageCamera);


        changeCamera = new ChangeCamera(cameraPhysic,stageCamera);
        ActConstants.publicInformation.put("changeCamera",changeCamera);



        this.addListener(new UserInputListener());




        tiledMap = new TmxMapLoader().load("core/assets/JX/JX.tmx");

        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //transfor object to entiy
        LoadTiledMap.doScene2(world,this,tiledMap);


        cameraFocus.directFocusOn(0,10.8f);


        ActConstants.publicInformation.replace("CurrentStage",this);


        AudioManager.instance.play(AssetsLevel0.instance.music.startTheme);



        batch = new SpriteBatch();

        cameraGUI = new OrthographicCamera(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);
        cameraGUI.position.set(0,0,0);
        cameraGUI.setToOrtho(true);

        cameraGUI.update();



        //*********************   public listen
        new BulletAndMonsterA();
        new MonsterASensorAndMainCharacter();

        new BulletDispose();
        new BulletAndMain();

        new GroundAndMainCharacter();

        new PortalAndMainCharacter();
        new MainCharacterAndBullet();

        //*********************  actors
        //region1
        this.addActor(new Fairy(1, AssetsLevel0.instance.fijling.animBreath,AssetsLevel0.instance.fijling.animAttack, (int) (44.5*50), (int) (14*50),ActConstants.windFairyID,world,"WindFairy"));
        new WindFairyAndMainCharacter();

        MonsterA monsterA = new MonsterA(world,123.6f,2.2f,1,AssetsUI.instance.cdxgw.animBreath,AssetsUI.instance.cdxgw.animDead);
        this.addActor(monsterA);

        this.addActor(new Portal(5690,104,239,5,ActConstants.portalID,world,"Portal1",true,"none"));


        //Region two
        this.addActor(new brokenBridge( AssetsLevel0.instance.xianjing.main,AssetsLevel0.instance.xianjing.main, 286, 3,5f,0.26f, ActConstants.brokenBridgeID, world, "brokenBridge"));        new BridgeAndMainCharacter();
//
        MonsterA monsterA2 = new MonsterA(world,350f,13f,1,AssetsUI.instance.cddpxgw.animBreath,AssetsUI.instance.cddpxgw.animDead);
        this.addActor(monsterA2);

        this.addActor(new Portal(18150,600,505,3,ActConstants.portalID,world,"Portal2",true,"none"));


        //Region3
        this.addActor(new Boss1(world,499,13));
        new Boss1SensorContact();
        new Boss1AndMainCharacter();
        this.addActor(new Portal(37860,148,505,3,ActConstants.portalID,world,"Portal3",true,"Stage2"));
//        this.addActor(new Portal(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate,47*50, (int) (6.2*50),505,3,ActConstants.portalID,world,"Portal3",true,"Stage2"));


        cameraFocus.directFocusOn(27,9.5f);
    }

    @Override
    public void act() {
        super.act();

        world.step(1.0f/60.0f, 6,6);


        if(ActConstants.physicalActionList.size()!=0){
            for(int i = 0; i<ActConstants.physicalActionList.size(); i++){
                ActConstants.physicalActionList.get(i).act();
                ActConstants.physicalActionList.remove(i);
            }
        }

        AssetsUI.instance.updateSprite();


        //the frame of camera to cover mainActor
        cameraFocus.LRBoundary(2,2,19,8);


        cameraPhysic.update();





        cameraGUI.update();

    }

    @Override
    public void draw() {



        //draw tiled map
        orthogonalTiledMapRenderer.setView(this.stageCamera);


//        orthogonalTiledMapRenderer.getBatch().begin();
//        orthogonalTiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("L2"));
        orthogonalTiledMapRenderer.render();


       // System.out.println(tiledMap.getLayers().get("L2")==null);
//        orthogonalTiledMapRenderer.render();

        //draw the entity
//        boxRender.render(world, cameraPhysic.combined);//结合相机进行绘制



        super.draw();
//        orthogonalTiledMapRenderer.renderTileLayer((TiledMapTileLayer) tiledMap.getLayers().get("L1"));
//        orthogonalTiledMapRenderer.getBatch().end();
//        if(ActConstants.isChange) {
//            this.dispose();
//            MyGdxGame.currentStage = new Stage2(ActConstants.inputMultiplexer);
//        }



        renderGui(batch);
    }

    public World getWorld(){
        return world;
    }

    @Override
    public void dispose() {
        super.dispose();
//        AudioManager.instance.stopMusic();
    }

    public void renderGui(SpriteBatch batch){
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();

        AssetsUI.instance.drawUpdate(batch);
        batch.end();
    }
}
