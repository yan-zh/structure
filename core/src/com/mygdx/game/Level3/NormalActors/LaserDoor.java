package com.mygdx.game.Level3.NormalActors;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.DieAction;
import com.mygdx.game.abstraction.UserData;

public class LaserDoor extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    PolygonShape shape;

    float physicalX;
    float physicalY;

    TextureRegion currentFrame;

    public boolean remove;

    public LaserDoor(World world, float physicalX, float physicalY) {
        remove = false;
        this.physicalX = physicalX;
        this.physicalY = physicalY;
        this.world = world;

        //创建物理模拟
        PhysicalEntityDefine.defineStatic();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();


        shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,8f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor=false;

        myBodyDef.position.set(physicalX,physicalY);//这个表示物理世界中的米


        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        mySimulation.createFixture(myFixtureDef).setUserData(new UserData(ActConstants.LaserDoorID,"LaserDoor"));

        ActConstants.publicInformation.put("LaserDoor",this);

        currentFrame = (TextureRegion) Assets.instance.bunny.animCopterTransform.getKeyFrame(0);
    }


    @Override
    public void act(float delta) {
        super.act(delta);

        if(remove==true){
            remove();
        }




    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);

    }

    @Override
    public boolean remove() {


        synchronized (ActConstants.MonsterActionLock){
            MyGdxGame.currentStage.addActor(new DieAction(Assets.instance.bunny.animNormal,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f));
            //消除自身，要锁

            DeletePhysicalEntity deletePhysicalEntity1 = new DeletePhysicalEntity();
            deletePhysicalEntity1.deleteBody(mySimulation,world);


            synchronized (ActConstants.physicalActionListLock){
                ActConstants.physicalActionList.add(deletePhysicalEntity1);
            }


            synchronized (ActConstants.publicInformationLock){
                ActConstants.publicInformation.remove("LaserDoor");
            }
            return super.remove();
        }
    }
}
