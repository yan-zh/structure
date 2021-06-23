package com.mygdx.game.Level2;

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
import com.mygdx.game.Level1.ContactReactions.BridgeAndMainCharacter;
import com.mygdx.game.Level1.ContactReactions.BulletAndMonsterA;
import com.mygdx.game.Level1.ContactReactions.MonsterASensorAndMainCharacter;
import com.mygdx.game.Level2.ContactReactions.*;
import com.mygdx.game.Level2.NormalActors.*;
import com.mygdx.game.Level3.ContactReactions.*;
import com.mygdx.game.PublicContactReaction.*;
import com.mygdx.game.Level3.NormalActors.Platform;
import com.mygdx.game.Listeners.PhysicalContactListener;
import com.mygdx.game.Listeners.UserInputListener;
import com.mygdx.game.Tools.*;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.*;


public class Stage2 extends MyStage {


    Box2DDebugRenderer boxRender;//
    OrthographicCamera cameraPhysic;//
    OrthographicCamera stageCamera;//

    CameraFocus cameraFocus;
    private OrthographicCamera cameraGUI;
    private SpriteBatch batch;


    TiledMap tiledMap;
    OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;


    public Stage2(InputMultiplexer inputMultiplexer){

        AssetsLevel0.instance.init(new AssetManager());
//        AssetsLevel2.instance.init(new AssetManager());

        ActConstants.changeStageTo = 0;
        AudioManager.instance.play(AssetsLevel0.instance.music.foresttheme);
        cUp=300000;
        cDown=-300000;
        cleft=-300000;
        cright=300000;




        System.out.println("新建了一个Stage2");
        inputMultiplexer.addProcessor(this);

        world = new World(new Vector2(0,-10),true);
        world.setContactListener(new PhysicalContactListener());
        PhysicalEntityDefine.boundWorld(world);


        this.addActor(new MainCharacter(world,7, 66));//
        this.addActor(new Beacon(7, 66, ActConstants.beaconID, world, "Beacon"));


        boxRender = new Box2DDebugRenderer();//
        cameraPhysic = new OrthographicCamera();//
        cameraPhysic.setToOrtho(false, (ActConstants.SCREEN_WIDTH)/ ActConstants.worldSize_pAndPhysic,(ActConstants.SCREEN_HEIGHT)/ ActConstants.worldSize_pAndPhysic);//这个是摄像机的框的大小，摄像机取景后还要经过最外层界面的压缩得到最终效果
        stageCamera = (OrthographicCamera) this.getCamera();
        stageCamera.setToOrtho(false, ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);

        cameraFocus = new CameraFocus(cameraPhysic, stageCamera);


        AssetsUI.instance.addSprit();

        this.addListener(new UserInputListener());//


        tiledMap = new TmxMapLoader().load("core/assets/SL/SL01.tmx");
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        LoadTiledMap.doScene2(world,this,tiledMap);


        cameraFocus.focusOn(0,5,1);//

        ActConstants.publicInformation.replace("CurrentStage",this);



        batch = new SpriteBatch();
        cameraGUI = new OrthographicCamera(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);
        cameraGUI.position.set(0,0,0);
        cameraGUI.setToOrtho(true);
        cameraGUI.update();

        new WoodFairyAndMainCharacter();
        new BulletAndMonsterA();
        new MonsterASensorAndMainCharacter();
        new PortalAndMainCharacter();
        new BulletDispose();
        new GroundAndMainCharacter();
        new MainCharacterAndSpine();
        new BridgeAndMainCharacter();
        new BulletAndMain();
        new MainCharacterAndBullet();



//yzh***************************************************





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





        Flower flower = new Flower(world,64-1.2f,13.4f,AssetsLevel0.instance.srh.animBreath, AssetsLevel0.instance.srh.animBreath,AssetsLevel0.instance.srh.animBreath);
        this.addActor(flower);
        new FlowerAndMainCharacter();

        this.addActor(new Fairy(2, AssetsUI.instance.mxjling.animBreath,AssetsUI.instance.mxjling.animBreath, (int) (30*50),10*50,ActConstants.woodFairyID,world,"WoodFairy"));
        new WoodFairyAndMainCharacter();


        this.addActor(new ReverberateAxe(world,86.5f-1.2f,45.5f-23.4f));
        new AxeAndMainCharacter();
        new AxeSensorContact();


        this.addActor(new Frag2(world,142-1.2f,32-23.4f));
        new HeatFragEye();
        this.addActor(new Frag2(world,147-1.2f,45-23.4f));
        this.addActor(new Frag2(world,165-1.2f,65-23.4f));

        this.addActor(new rotateSwitch(AssetsUI.instance.jiguan.jg0, AssetsUI.instance.jiguan.jg1, 183f-1.2f, 64f-23.4f, ActConstants.switchID, world,"rotateSwitchDoor", "door"));
        this.addActor(new Door(Assets.instance.goldCoin.animGoldCoin,Assets.instance.bunny.getAnimCopterRotate, 193f-1.2f, 39.5f-23.4f,1f/ ActConstants.worldSize_shapeAndPhysics, 7f/ ActConstants.worldSize_shapeAndPhysics, ActConstants.switchID, world, "door"));

        this.addActor(new rotateSwitch(AssetsUI.instance.jiguan.jg0, AssetsUI.instance.jiguan.jg1, 92f, 24f, ActConstants.switchID, world,"rotateSwitchFrag", "frag"));



//*****************************************

        ActConstants.publicInformation.put("stage2", this);


        this.addActor(new Platform(world,27-1.2f,88-23.4f,3f,5f,ActConstants.platformID,"Platform"));
        this.addActor(new Platform(world,84-1.2f,80-23.4f,3f,5f,ActConstants.platformID,"Platform"));


        this.addActor(new ThinSurface(world,54.1f-1.2f,65-23.4f,6.3f,0.5f,
                ActConstants.thinSurfaceID,"thinSurface"));
        this.addActor(new HangedBalls(world,203-1.2f,65,"01201",ActConstants.hangedBallsID,"hangedBalls"));
//        this.addActor(new SleepingBear(world,212-1.2f,10-23.4f,5f,3f,ActConstants.BearID,"sleepingBear"));
//        this.addActor(new Bubbles(world,216-1.2f,10-23.4f,15,0.7f,ActConstants.BubbleID,"bubbles"));
        new ThinSurfaceContact();
        new BallsContact();
        new BearWindContact();
        new PlatformAndMainCharacter();
        new BearAndMainCharacter();
        new BubbleAndCharacter();
        this.addActor(new PswDoor(world,64f-1.2f,82f-23.4f,11f,1f,ActConstants.PswDoorID,"pswDoor"));
        new PswDoorContact();
        this.addActor(new Portal(14000,1200,505,3,ActConstants.portalID,world,"Portal1",true,"Stage3"));
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


        cameraFocus.innerBoundary(2,2,19,8);

        cameraPhysic.update();

        cameraGUI.update();
    }

    @Override
    public void draw() {


        orthogonalTiledMapRenderer.setView(this.stageCamera);
        orthogonalTiledMapRenderer.render();



        super.draw();//
        //
        AssetsUI.instance.updateSprite();
        renderGui(batch);
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
