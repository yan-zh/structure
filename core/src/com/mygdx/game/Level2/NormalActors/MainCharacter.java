package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Level2.PhysicalActions.AdjustPosition;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.UserData;
import org.graalvm.compiler.lir.LIRInstruction;


public class MainCharacter extends Actor {

    World world;
    public Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;



    float statetime;//用于替换主角动作图片的标记

    Animation test;//这个就是


    int jumpNumber;

    boolean die;


    Animation walkLeft;
    Animation walkRight;

    Animation hitLeft;
    Animation hitRight;

    Animation reborn;

    boolean goLeft;

    float timeWalk;
    float timeHit;
    float timeDie;

    boolean hurt;

    TextureRegion currentFrame;//当前该播放的图片（这个类是从texture中切一块出来）
    public MainCharacter(World world,float x,float y){

        walkLeft = AssetsUI.instance.mainUser.animRun;
        hitLeft = AssetsUI.instance.mainUser.animAttack;

        walkRight = AssetsUI.instance.mainUser.animRun;
        hitRight = AssetsUI.instance.mainUser.animAttack;

        reborn = AssetsUI.instance.mainUser.animJump;

        timeWalk = 0;
        timeHit = 0;
        timeDie = 0;

        hurt = false;

        goLeft= true;

        die=false;
        //获得物理世界引用
        this.world = world;
        jumpNumber=2;

        //创建主角物理模拟
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
       // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;
        myFixtureDef.isSensor = false;


        myBodyDef.position.set(x,y);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.mainCharacterID,"MainCharacter"));

        mySimulation.setGravityScale(1);







        //内存显示区
        this.statetime = 0;
        prepareForPicture();


        //交互注册
        ActConstants.publicInformation.put("MainCharacter",this);

        ActConstants.mainCharacter = this;

    }

    //Cancel Jump
    public void cancelJump(){
        ActConstants.MainCharacterState.replace("onGround",false);
        ActConstants.MainCharacterState.replace("repulse",false);
    }

    public void prepareForPicture() {

        test = Assets.instance.mainCharacter.animBreath;
//
//        Action delayedAction = Actions.run(new Runnable() {
//
//
//
//            @Override
//
//            public void run() {
//
//                System.out.println("time:" + (System.currentTimeMillis() / 1000) + ",执行something");
//
//            }
//
//        });
//
//        Action action = Actions.delay(20f,delayedAction);//这个数就是20s
//
//        this.addAction(Actions.sequence(action));

    }

    @Override
    public void act(float delta) {
        if(die==false){
            timeWalk += delta;

            //动作状态改变
            if(ActConstants.MainCharacterState.get("goLeft")){
                goLeft=true;
                mySimulation.setLinearVelocity(-ActConstants.MainCharacterSpeed,mySimulation.getLinearVelocity().y);

            }else if(ActConstants.MainCharacterState.get("goRight")){
                goLeft=false;
                mySimulation.setLinearVelocity(ActConstants.MainCharacterSpeed,mySimulation.getLinearVelocity().y);
            }

            if(ActConstants.MainCharacterState.get("blow")){
                mySimulation.setLinearVelocity(mySimulation.getLinearVelocity().x,5);
            }


            if(hurt==false){
                currentFrame = (TextureRegion) walkLeft.getKeyFrame(timeWalk,true);
            }else{
                timeHit += delta;
                currentFrame = (TextureRegion) hitLeft.getKeyFrame(timeHit,false);
                if(hitLeft.isAnimationFinished(timeHit)){
                    timeHit = 0;
                    hurt = false;
                }
            }

        }else{
            timeDie += delta;
            currentFrame = (TextureRegion) reborn.getKeyFrame(timeDie,false);
            if(reborn.isAnimationFinished(timeDie)){
                timeDie = 0;
                die=false;
                reborn();
            }
        }


        if(mySimulation.getPosition().y<-50){
            die();
        }


        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(goLeft==false) {
            if (!currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }else{
            if (currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }


        batch.draw(currentFrame, (mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);//把模拟物体的坐标拿出来，转换一下画上去

    }

    public void jump(){
        if(jumpNumber>=1){
            float gravityScale;
            gravityScale = mySimulation.getGravityScale();
            if(gravityScale==1){
                mySimulation.setLinearVelocity(mySimulation.getLinearVelocity().x,0);
                mySimulation.applyLinearImpulse(new Vector2(0, ActConstants.MainCharacterUpImpulse),mySimulation.getPosition(),true);
            }else{
                mySimulation.setLinearVelocity(mySimulation.getLinearVelocity().x,0);
                mySimulation.applyLinearImpulse(new Vector2(0, -ActConstants.MainCharacterUpImpulse),mySimulation.getPosition(),true);
            }
            jumpNumber--;
        }


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
        die=true;

    }

    public void reborn(){
        Beacon beacon = ((Beacon) ActConstants.publicInformation.get("Beacon"));
        float[] data = beacon.returnState();
        resetPosition(data[0],data[1]);
        ActConstants.health = (int)data[2];
    }

    public void reFreshJump(){
        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.MainCharacterState.replace("repulse",false);
        jumpNumber=2;
    }

    public void startGravityInverse(){
        float scale = mySimulation.getGravityScale();
        if(scale==1){
            scale = -1;
        }else{
            scale = 1;
        }
        mySimulation.setGravityScale(scale);
    }

    public boolean damage(int damage){
        synchronized (ActConstants.mainCharacterLock){
            hurt = true;
            ActConstants.health = ActConstants.health-damage;

            if(ActConstants.health<=0){
                die=true;
                return false;
            }

            return true;
        }
    }

    public void resetPosition(float x, float y){
        AdjustPosition adjustPosition = new AdjustPosition(x/50,y/50);
        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(adjustPosition);
        }

    }


}
