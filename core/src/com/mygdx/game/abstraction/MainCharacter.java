package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Level2.PhysicalActions.AdjustPosition;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.Beacon;
import com.mygdx.game.abstraction.UserData;
//import org.graalvm.compiler.lir.LIRInstruction;


public class MainCharacter extends Actor {

    World world;
    public Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;



    float statetime;//used to change picutre of animation

    Animation test;


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

    TextureRegion currentFrame;

    public MainCharacter(World world,float x,float y){

        walkLeft = AssetsUI.instance.zj.animBreath;
        hitLeft = AssetsUI.instance.zj.animDead;

        walkRight = AssetsUI.instance.zj.animBreath;
        hitRight = AssetsUI.instance.zj.animDead;

        reborn = AssetsUI.instance.decoration.animChongsheng;

        timeWalk = 0;
        timeHit = 0;
        timeDie = 0;

        hurt = false;

        goLeft= true;

        die=false;

        this.world = world;
        jumpNumber=2;

        //create entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        //2.5  2.2还可以
        myFixtureDef.shape = shape;
        myFixtureDef.isSensor = false;



        myBodyDef.position.set(x,y);//use m

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.mainCharacterID,"MainCharacter"));

        mySimulation.setGravityScale(1);







        this.statetime = 0;
        prepareForPicture();


        ActConstants.publicInformation.put("MainCharacter",this);

        ActConstants.mainCharacter = this;

    }

    //call it, then cannot jump
    public void cancelJump(){
        ActConstants.MainCharacterState.replace("onGround",false);
        ActConstants.MainCharacterState.replace("repulse",false);
    }

    //just for test
    public void prepareForPicture() {

        test = Assets.instance.mainCharacter.animBreath;


    }

    @Override
    public void act(float delta) {
        if(die==false){
            timeWalk += delta;

            //change physical action according to stage
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


            //change picture according to state
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


        if(mySimulation.getPosition().y<-1){
            die();
        }


        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //face to direction
        if(goLeft==false) {
            if (!currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }else{
            if (currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }


        batch.draw(currentFrame, (mySimulation.getPosition().x-1.25f)*50f, (mySimulation.getPosition().y-1.35f)*50f);//把模拟物体的坐标拿出来，转换一下画上去

    }

    //most 2 jump
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

        AudioManager.instance.play(AssetsUI.instance.sounds.jump_MainCharacter);


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


    //call it when being attacted
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

        AudioManager.instance.play(AssetsUI.instance.sounds.seinHurtRegularA);


    }


    public void die(){

        System.out.println("die");
        die=true;
        AudioManager.instance.play(AssetsUI.instance.sounds.seinDeathLaserA);

    }

    public void reborn(){
        Beacon beacon = ((Beacon) ActConstants.publicInformation.get("Beacon"));
        float[] data = beacon.returnState();
        resetPosition(data[0],data[1]);
        AssetsUI.instance.addLives(20);
    }

    //call it when on ground
    public void reFreshJump(){
        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.MainCharacterState.replace("repulse",false);
        jumpNumber=2;
    }

    //for test
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


    //set new position
    public void resetPosition(float x, float y){
        AdjustPosition adjustPosition = new AdjustPosition(x/50,y/50);
        synchronized (ActConstants.physicalActionListLock){
            ActConstants.physicalActionList.add(adjustPosition);
        }

    }


}
