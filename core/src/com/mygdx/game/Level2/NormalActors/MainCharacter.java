package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;
import org.graalvm.compiler.lir.LIRInstruction;


public class MainCharacter extends Actor {
    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;


    float statetime;//用于替换主角动作图片的标记

    Animation test;//这个就是


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
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.mainCharacterID,"MainCharacter"));


        //内存显示区
        this.statetime = 0;
        prepareForPicture();


        //交互注册
        ActConstants.publicInformation.put("MainCharacter",this);



    }

    public void prepareForPicture() {

        test = Assets.instance.mainCharacter.animBreath;

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
        if(ActConstants.MainCharacterState.get("goLeft")){
            mySimulation.setLinearVelocity(-ActConstants.MainCharacterSpeed,mySimulation.getLinearVelocity().y);

        }
        if(ActConstants.MainCharacterState.get("goRight")){
            mySimulation.setLinearVelocity(ActConstants.MainCharacterSpeed,mySimulation.getLinearVelocity().y);
        }





        statetime += delta;//用于调整主角要展示的图片的时间标记,****************这里调整了，用了传入的delta，看看行不行

        currentFrame = (TextureRegion)test.getKeyFrame(statetime,true);

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame, (mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);//把模拟物体的坐标拿出来，转换一下画上去

    }

    public void jump(){
        mySimulation.applyLinearImpulse(new Vector2(0, ActConstants.MainCharacterUpImpulse),mySimulation.getPosition(),true);
    }

    public Body getMySimulation(){
        return mySimulation;
    }

    public Vector2 getPositionInSimulation(){
        return mySimulation.getPosition();
    }

    @Override
    public float getX() {
        return mySimulation.getPosition().x*ActConstants.worldSize_pAndPhysic;
    }

    @Override
    public float getY() {
        return mySimulation.getPosition().y*ActConstants.worldSize_pAndPhysic;
    }

    public float getPhysicalX(){
        return mySimulation.getPosition().x;
    }

    public float getPhysicalY(){
        return mySimulation.getPosition().y;
    }


    public void repulse(float spineX, float spineY){
        mySimulation.setLinearVelocity(0,0);
        ActConstants.MainCharacterState.replace("repulse",true);
        ActConstants.MainCharacterState.replace("goLeft",false);
        ActConstants.MainCharacterState.replace("goRight",false);
        float myX = mySimulation.getPosition().x;
        float myY = mySimulation.getPosition().y;
        if(spineX>=myX){
            mySimulation.applyLinearImpulse(-100,400,myX,myY,true);
        }else{
            mySimulation.applyLinearImpulse(100,400,myX,myY,true);
        }

    }


    public void die(){
        System.out.println("die");
    }
}
