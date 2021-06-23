package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class Spine extends Actor {

    public static int number=0;

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;


//    float statetime;

//    Animation spine;//这个就是

    boolean draw;


    TextureRegion currentFrame;
    public Spine(World world, float x, float y, float height, float width) {

        draw=true;

        number++;

        ActConstants.publicInformation.put("Spine"+Integer.toString(number),this);

        this.world = world;


        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
        shape.setAsBox(width/ ActConstants.worldSize_shapeAndPhysics,height/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;
        myFixtureDef.isSensor = false;

        myBodyDef.position.set(x,y);

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.spineID,"Spine"+number));

        this.setX(mySimulation.getPosition().x*ActConstants.worldSize_pAndPhysic);
        this.setY(mySimulation.getPosition().y*ActConstants.worldSize_pAndPhysic);



    }

    @Override
    public void act(float delta) {
//        //if(draw==true){
//            statetime+=delta;
//            currentFrame = (TextureRegion) spine.getKeyFrame(statetime);
//        //}
//
        super.act(delta);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        //if(draw==true){
//            batch.draw(currentFrame,getX(),getY());
//       // }
        super.draw(batch, parentAlpha);

    }


    public float getPhysicalX(){
        return mySimulation.getPosition().x;
    }

    public float getPhysicalY(){
        return mySimulation.getPosition().y;
    }

    public void setVelocity(float x, float y){
        mySimulation.setLinearVelocity(x,y);
    }

//    public void setDraw(boolean draw){
//        this.draw = draw;
//    }

    public Body getSimulation(){
        return mySimulation;
    }

}
