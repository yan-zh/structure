package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class Axe extends Actor {
    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;


    Body sensorSimulation;
    FixtureDef sensorFixtureDef;
    BodyDef sensorBodyDef;
    PolygonShape sensorshape;


    float statetime;//用于替换主角动作图片的标记

    TextureRegion picutre;//这个就是

    float physicalX;
    float physicalY;

    short mark;

    public Axe(World world, float x, float y) {

        mark=0;

        //获得物理世界引用
        this.world = world;

        //创建主角物理模拟
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();
        myFixtureDef.isSensor = true;
        myBodyDef.gravityScale = 0;



        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(x,y);//这个表示物理世界中的米

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.axeID,"Axe"));





        //创建sensor
        PhysicalEntityDefine.defineStatic();
        sensorBodyDef = PhysicalEntityDefine.getBd();
        sensorFixtureDef = PhysicalEntityDefine.getFd();

        sensorFixtureDef.isSensor = true;






        sensorshape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        sensorshape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        sensorFixtureDef.shape = sensorshape;

        sensorBodyDef.position.set(x-2,y-5);//这个表示物理世界中的米

        sensorSimulation = world.createBody(sensorBodyDef);

        sensorSimulation.createFixture(sensorFixtureDef).setUserData(new UserData(ActConstants.axeSensorID,"Axe"));


        ActConstants.publicInformation.put("Axe",this);


        this.picutre = (TextureRegion) Assets.instance.goldCoin.animGoldCoin.getKeyFrames()[0];

    }


    @Override
    public void act(float delta) {

        super.act(delta);
        if(mySimulation!=null){
            physicalX = mySimulation.getPosition().x;
            physicalY = mySimulation.getPosition().y;
        }

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);



        batch.draw(picutre,(physicalX-0.7f)*50f, (physicalY-0.45f)*50f);
    }

    public void fall(){

        mySimulation.setGravityScale(1);

    }

    public void stop(){

        if(mark==0){



            Timer timer = new Timer();

            Timer.Task timerTask = new Timer.Task() {
                @Override

                public void run() {


                    Axe axe = (Axe) ActConstants.publicInformation.get("Axe");
                    if(axe!=null){

                        mySimulation.setGravityScale(0);
                        mySimulation.setLinearVelocity(0,0);


                        axe.remove();
                    }


                }

            };

            timer.scheduleTask(timerTask, 2, 0, 1);// 0s之后执行，每次间隔1s，执行20次。


        }

        mark++;
        System.out.println(mark);

    }


    @Override
    public boolean remove() {

        DeletePhysicalEntity deletePhysicalEntity1 = new DeletePhysicalEntity();
        deletePhysicalEntity1.deleteBody(mySimulation,world);
        DeletePhysicalEntity deletePhysicalEntity2 = new DeletePhysicalEntity();
        deletePhysicalEntity2.deleteBody(sensorSimulation,world);

        ActConstants.physicalActionList.add(deletePhysicalEntity1);
        ActConstants.physicalActionList.add(deletePhysicalEntity2);
        ActConstants.publicInformation.remove("Axe");
        return super.remove();
    }
}
