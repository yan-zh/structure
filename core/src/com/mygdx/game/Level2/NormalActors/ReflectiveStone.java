package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.UserData;

public class ReflectiveStone extends Actor {
    public Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    Image image;

    public boolean state = true;

    String name;

    World world;

    static int number=0;
    int myNumber;


    int type;//1 left   2 right  3 up
    public ReflectiveStone(int x, int y, long actorId, World world,int type)
    {

        number++;
        myNumber = number;

        this.type = type;
        //Set position
        this.setX(x);
        this.setY(y);
        TextureRegion texture = (TextureRegion) Assets.instance.mainCharacter.animRun.getKeyFrames()[0];
        //image = new Image(new Texture("D:\\structurenew\\core\\assets\\images\\anim_bunny_copter_01.png"));
        image = new Image(texture);
        image.setSize(1.0f, 1.0f);
        image.setOrigin(image.getWidth() / 2.0f, image.getHeight() / 2.0f);
        //Create the physical Entity
        PhysicalEntityDefine.defineKinematic();
        myBodyDef = PhysicalEntityDefine.getBd();
       // myBodyDef.type = BodyDef.BodyType.;
        myFixtureDef = PhysicalEntityDefine.getFd();

        myFixtureDef.isSensor = false;




        //这里设定盒子的大小
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;


        myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);
        myBodyDef.fixedRotation = true;

        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        myFixture = mySimulation.createFixture(myFixtureDef);
        mySimulation.setFixedRotation(false);
        mySimulation.setUserData(image);
        myFixture.setUserData(new UserData(actorId, "ReflectiveStone"+myNumber));
        mySimulation.setGravityScale(1);
        ActConstants.publicInformation.put("ReflectiveStone"+myNumber, this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);



    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion textureRegion = new TextureRegion(new Texture("images/anim_bunny_copter_01.png"));
        batch.draw(textureRegion, (mySimulation.getPosition().x - 0.7f)* 50f, (mySimulation.getPosition().y - 0.45f)*50f,(float)0.5*textureRegion.getRegionWidth(),(float)0.5*textureRegion.getRegionHeight(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),(float) 1.0,(float) 1.0, (float) (180*mySimulation.getAngle()/3.14),true);

    }
    public void emmit()
    {

        float[] direction = new float[2];
        if(type==1){
            direction = MyVector.getStandardVector(0,0,-1,0);
        }else if(type==2){
            direction = MyVector.getStandardVector(0,0,1,0);
        }else if(type==3){
            direction = MyVector.getStandardVector(0,0,0,1);
        }

        System.out.println("Emmit");
        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.getAnimCopterRotate,Assets.instance.bunny.animNormal,Assets.instance.mainCharacter.animRun,this.getX(),this.getY(),direction,ActConstants.laserID,1));
    }

    @Override
    public boolean remove() {
        ActConstants.publicInformation.remove(name);
        return super.remove();
    }


    public void removeBody(){
        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(mySimulation,world);
        ActConstants.physicalActionList.add(deletePhysicalEntity);
    }

}
