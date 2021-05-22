package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import jdk.internal.loader.Resource;


public class MainCharacter extends Actor {
    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;


    float statetime;//用于替换主角动作图片的标记
    Texture texture;//装载图片的
    Animation aniRight;
    Animation aniLeft;
    Animation aniIdle;
    Animation test;

    //老状态
    float state1;
    float state;
    boolean ifStill;


    Sprite sprite;
    Texture texture1;

    TextureRegion currentFrame;//当前该播放的图片（这个类是从texture中切一块出来）
    public MainCharacter(World world,float x,float y){
        //获得物理世界引用
        this.world = world;

        //创建主角物理模拟
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
       // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(1f/PublicData.worldSize_shapeAndPhysics,1.5f/PublicData.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(10,30.9f);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        mySimulation.createFixture(myFixtureDef).setUserData("main character");


        //内存显示区
        this.setX(x);
        this.setY(y);
        this.statetime = 0;
        prepareForPicture();


        //状态设置 这是老状态，需要新的
        state = PublicData.marioIdle;
        state1 = PublicData.NoLevelMotion;
        ifStill = true;


        texture1 = new Texture(Gdx.files.internal("ground.png"));//加载九宫格图片
        sprite = new Sprite(texture1);
        sprite.setSize(30*PublicData.worldSize_pAndPhysic,5f*PublicData.worldSize_pAndPhysic);


    }

    public void prepareForPicture() {
        texture = new Texture(Gdx.files.internal("core/assets/mario.jpg"));//加载九宫格图片

        TextureRegion[][] spilt = TextureRegion.split(texture, 64, 64);//好像是从左上角开始，每64像素且一个片，一次放到这个数组里，这个数组就是由region组成的
        TextureRegion[][] miror = TextureRegion.split(texture, 64, 64);//用于反向走的

        for(TextureRegion[] region1 : miror){//进行反向处理
            for(TextureRegion region2 : region1){
                region2.flip(true, false);//反转图片
            }
        }


        //右方向的动画类
        TextureRegion[] regionR = new TextureRegion[3];
        regionR[0] = spilt[0][1];
        regionR[1] = spilt[0][2];
        regionR[2] = spilt[0][0];
        aniRight = new Animation(0.1f, regionR);//播放速度和播放的图片数组
        //左方向
        TextureRegion[] regionL = new TextureRegion[3];
        regionL[0] = miror[0][1];
        regionL[1] = miror[0][2];
        regionL[2] = miror[0][0];
        aniLeft = new Animation(0.1f, regionL);
        //空闲动作
        TextureRegion[] regionI = new TextureRegion[1];
        regionI[0] = spilt[0][0];

        aniIdle = new Animation(0.1f, regionI);

        test = Assets.instance.bunny.animNormal;

        Action delayedAction = Actions.run(new Runnable() {



            @Override

            public void run() {

                System.out.println("time:" + (System.currentTimeMillis() / 1000) + ",执行something");

            }

        });

        Action action = Actions.delay(20f,delayedAction);//这个数就是20s

        this.addAction(Actions.sequence(action));

    }

    @Override
    public void act(float delta) {

        //动作状态改变
        if(PublicData.MainCharacterState.get("goLeft")){
            mySimulation.setLinearVelocity(-PublicData.MainCharacterSpeed,mySimulation.getLinearVelocity().y);

        }
        if(PublicData.MainCharacterState.get("goRight")){
            mySimulation.setLinearVelocity(PublicData.MainCharacterSpeed,mySimulation.getLinearVelocity().y);
        }





        statetime += delta;//用于调整主角要展示的图片的时间标记,****************这里调整了，用了传入的delta，看看行不行

//        if(state == PublicData.marioLeft) {
//            currentFrame = (TextureRegion)aniLeft.getKeyFrame(statetime, true);//根据当前“时间”提供该播放的图片，后面的true是循环播放
//        }else if (state == PublicData.marioRight) {
//            currentFrame = (TextureRegion)aniRight.getKeyFrame(statetime, true);
//        }else if (state == PublicData.marioIdle) {
//            currentFrame = (TextureRegion)aniIdle.getKeyFrame(statetime, true);
//        }
        currentFrame = (TextureRegion)test.getKeyFrame(statetime,true);

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(currentFrame, (mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);//把模拟物体的坐标拿出来，转换一下画上去

      //  batch.draw(currentFrame, 125, 200);
        //batch.draw(texture1,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
        super.draw(batch, parentAlpha);
    }

    public void goRight(){
        state1 = PublicData.CharacterRight;
        state = PublicData.marioRight;
        ifStill = false;
        //world.clearForces();
        //mySimulation.setLinearVelocity(0,mySimulation.getLinearVelocity().y);
        // mySimulation.applyForce(new Vector2(MainCharacterLevelForce,0),mySimulation.getPosition(),true);
        //mySimulation.applyLinearImpulse(new Vector2(MianCharacterLevelImpulse,0),mySimulation.getPosition(),true);
        //System.out.println(System.currentTimeMillis()); // 毫秒
        // time = System.currentTimeMillis();




    }

    public void jump(){
        mySimulation.applyLinearImpulse(new Vector2(0,PublicData.MainCharacterUpImpulse),mySimulation.getPosition(),true);
        System.out.println("678");
    }

    public Body getMySimulation(){
        return mySimulation;
    }

    public void goLeft(){
        state1 = PublicData.CharacterLeft;
        state = PublicData.marioLeft;
        ifStill = false;
        // world.clearForces();
        // mySimulation.setLinearVelocity(0,mySimulation.getLinearVelocity().y);
        // mySimulation.applyForce(new Vector2(-MainCharacterLevelForce,0),mySimulation.getPosition(),true);
        // mySimulation.applyLinearImpulse(new Vector2(-MianCharacterLevelImpulse,0),mySimulation.getPosition(),true);
        // mySimulation.setLinearVelocity(-MainCharacterLevelSpeed,0);
        //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        //time = System.currentTimeMillis()-time;
        //System.out.println(time); // 毫秒
        //System.out.println(mySimulation.getLinearVelocity().x);
        //System.out.println((10*time/1000)/mySimulation.getLinearVelocity().x);
    }

    public void goUp(){
        mySimulation.applyLinearImpulse(new Vector2(0,PublicData.MainCharacterUpImpulse),mySimulation.getPosition(),true);
        //mySimulation.applyLinearImpulse(new Vector2(-500,0),mySimulation.getPosition(),true);
        //state = Data.marioIdle;
        ifStill = false;

        
    }

    public void upKey(int keyCode){
        if(keyCode == Input.Keys.D && state1 == PublicData.CharacterRight){
            state1 = PublicData.NoLevelMotion;
            state = PublicData.marioIdle;
            ifStill = true;

        }
        if(keyCode == Input.Keys.A && state1 == PublicData.CharacterLeft){
            state1 = PublicData.NoLevelMotion;
            state = PublicData.marioIdle;
            ifStill = true;
        }

    }


    //public Vector2 getPosition(){
    //    return new Vector2(this.getX(),this.getY());
    //}

    public Vector2 getPositionInSimulation(){
        return mySimulation.getPosition();
    }

}
