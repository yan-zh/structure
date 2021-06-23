package com.mygdx.game.Level3.NormalActors;

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
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.UserData;

public class StillReflectiveStone extends Actor{

    public Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    Image image;

    public boolean state = true;


    World world;

    static int number=0;
    int myNumber;

    TextureRegion currentFrame;


    int type;//1 left   2 right  3 up

    public StillReflectiveStone(int x, int y, long actorId, World world,int type)
    {

        number++;
        myNumber = number;

        this.type = type;
        //Set position
        this.setX(x);
        this.setY(y);

        //image = new Image(new Texture("D:\\structurenew\\core\\assets\\images\\anim_bunny_copter_01.png"));

        //Create the physical Entity
        PhysicalEntityDefine.defineKinematic();
        myBodyDef = PhysicalEntityDefine.getBd();
        // myBodyDef.type = BodyDef.BodyType.;
        myFixtureDef = PhysicalEntityDefine.getFd();

        myFixtureDef.isSensor = false;




        //
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
        myFixture.setUserData(new UserData(actorId, "stillReflectiveStone"+myNumber));
        mySimulation.setGravityScale(1);
        ActConstants.publicInformation.put("stillReflectiveStone"+myNumber, this);

        //
        currentFrame = new TextureRegion(AssetsLevel2.instance.decoration.pianzhuanqi);

    }

    @Override
    public void act(float delta) {
        super.act(delta);



    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(type==2){
            if(!currentFrame.isFlipX()){
                currentFrame.flip(true,false);
            }
        }

        batch.draw(currentFrame,(mySimulation.getPosition().x-1f)*50f, (mySimulation.getPosition().y-1f)*50f,100,100);

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
        MyGdxGame.currentStage.addActor(new BulletSkill(AssetsLevel2.instance.decoration.jiguang, AssetsUI.instance.spriteAttack.animFhit,this.getX(),this.getY(),direction,ActConstants.laserID,1,4));
    }

}
