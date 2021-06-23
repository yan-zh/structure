package com.mygdx.game.Tools;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.UserData;

public class PhysicalEntityDefine {//
    static BodyDef bd =new BodyDef();
    static FixtureDef fd = new FixtureDef();
    static World world;

    public PhysicalEntityDefine(){

    }

    public static void boundWorld(World w){
        world = w;
    }


    public static void createStandardBlock(float positionX, float positionY, float width, float height, float angle){

        BodyDef bd1 = new BodyDef();
        bd1.type = BodyDef.BodyType.StaticBody;
        bd1.active = true;
        bd1.allowSleep = true;
        bd1.awake = true;
        bd1.bullet = false;
        bd1.angle = (float)(angle*Math.PI/180);



        bd1.position.set(positionX,positionY);

        Vector2 oldVector;
        Vector2 newVector;
        Vector2 translate;


        oldVector = new Vector2(-(0.5f*width),0.5f*height);

        newVector = new Vector2((float)(Math.cos(bd1.angle)*oldVector.x-Math.sin(bd1.angle)*oldVector.y),(float)(Math.sin(bd1.angle)*oldVector.x+Math.cos(bd1.angle)*oldVector.y));

        translate = newVector.sub(oldVector);

        bd1.position.set(bd1.position.sub(translate));






        FixtureDef fd1 = new FixtureDef();
        fd1.density = 1;
        fd1.friction = 1f;
        fd1.restitution = 0;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width-0.1f)/ ActConstants.worldSize_shapeAndPhysics,height/ ActConstants.worldSize_shapeAndPhysics);
        fd1.shape = shape;

        Body b1 = world.createBody(bd1);
        b1.createFixture(fd1).setUserData(new UserData(ActConstants.groundID,"Ground"));

    }

    public static void defineCharacter(){

        bd.type = BodyDef.BodyType.DynamicBody;
        bd.active = true;//
        bd.allowSleep = false;//
        bd.bullet = true;//
        bd.fixedRotation = true;//
        bd.linearDamping = 0;//



        fd.restitution = 0f;//
        fd.friction = 1f;//
        fd.density = 40;

    }

    public static void defineStatic(){
        bd.type = BodyDef.BodyType.StaticBody;
        bd.active = true;//
        bd.allowSleep = false;
        bd.bullet = true;//
        bd.fixedRotation = true;//
        bd.linearDamping = 0;



        fd.restitution = 0f;
        fd.friction = 0f;
        fd.density = 40;
    }

    public static void defineKinematic(){
        bd.type = BodyDef.BodyType.KinematicBody;
        bd.active = true;//
        bd.allowSleep = false;//
        bd.bullet = true;//
        bd.fixedRotation = true;//
        bd.linearDamping = 0;



        fd.restitution = 0f;//
        fd.friction = 0f;//
        fd.density = 40;
    }

    public static void defineAttack(){

        bd.type = BodyDef.BodyType.DynamicBody;
        bd.active = true;//
        bd.allowSleep = false;//
        bd.bullet = true;//
        bd.fixedRotation = true;//
        bd.linearDamping = 0;//



        fd.restitution = 0f;//
        fd.friction = 0f;//
        fd.density = 5;

    }

    public static BodyDef getBd(){ return bd; }

    public static FixtureDef getFd(){
        return fd;
    }
}
