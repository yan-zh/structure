package com.mygdx.game.Level2.NormalActors;

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


    float statetime;//用于替换主角动作图片的标记

    Animation spine;//这个就是


    TextureRegion currentFrame;//当前该播放的图片（这个类是从texture中切一块出来）
    public Spine(World world, float x, float y) {

        number++;

        ActConstants.publicInformation.put("Spine"+Integer.toString(number),this);

        this.world = world;

        //创建主角物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();



        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(3f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.spineID,"Spine"+number));

        this.setX(mySimulation.getPosition().x*ActConstants.worldSize_pAndPhysic);
        this.setY(mySimulation.getPosition().y*ActConstants.worldSize_pAndPhysic);



        //内存显示区
        this.statetime = 0;

        this.spine = Assets.instance.characrer01.animBreath;

    }

    @Override
    public void act(float delta) {

        super.act(delta);
        statetime+=delta;
        currentFrame = (TextureRegion) spine.getKeyFrame(statetime);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(currentFrame,getX(),getY());
    }

    public float getPhysicalX(){
        return mySimulation.getPosition().x;
    }

    public float getPhysicalY(){
        return mySimulation.getPosition().y;
    }
}
