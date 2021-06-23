package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.BodyBuilder;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.UserData;

public class Platform extends Actor {

    World world;
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    PolygonShape shape;
    Texture texture;
    TextureRegion region;
    String name;
    Image image;
    TextureRegion textureRegion;
    float width;
    float height;


    public Platform(World world, float x, float y,float width, float height, long actorId,String name) {
        this.setX(x);
        this.setY(y);
        this.width=width;
        this.height=height;

        textureRegion = AssetsLevel0.instance.jiemi.qiuqian;
        image = new Image(textureRegion);



        this.world=world;
//        PhysicalEntityDefine.defineCharacter();
//        myBodyDef = PhysicalEntityDefine.getBd();
//        myFixtureDef = PhysicalEntityDefine.getFd();
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox( 2f / ActConstants.worldSize_shapeAndPhysics,
//                 2f / ActConstants.worldSize_shapeAndPhysics);
//        myFixtureDef.shape = shape;
//        myBodyDef.position.set(this.getX()/ActConstants.worldSize_pAndPhysic,this.getY()/ActConstants.worldSize_pAndPhysic);
//
//        mySimulation = world.createBody(myBodyDef);
//
//      myFixture = mySimulation.createFixture(myFixtureDef);
        mySimulation=BodyBuilder.createBox(world,this.getX(),
                this.getY(),width / ActConstants.worldSize_shapeAndPhysics,
height / ActConstants.worldSize_shapeAndPhysics,true,actorId,name
                );
//        myFixture.setUserData(new UserData(actorId,name));
        this.name = name;
        ActConstants.publicInformation.put(name,this);
        buildRopeJoint(mySimulation);

    }

    public void buildRopeJoint(Body hanging){
        Body vertex= BodyBuilder.createBox(world,this.getX(),this.getY()+15,
                10f/ActConstants.worldSize_pAndPhysic,
                10f/ActConstants.worldSize_pAndPhysic,false,0b0,"vertex" );




//        RopeJointDef rDef= new RopeJointDef();
        Vector2 position=new Vector2(this.getX(),this.getY()+15);
        RevoluteJointDef rDef= new RevoluteJointDef();
        rDef.initialize(vertex,hanging,position);

//        rDef.collideConnected=false;
//        rDef.maxLength=2f/ActConstants.worldSize_pAndPhysic;
//        rDef.localAnchorA.set(this.getX()/ActConstants.worldSize_pAndPhysic,this.getY()/ActConstants.worldSize_pAndPhysic);
//        rDef.localAnchorB.set(hanging.getPosition().x,hanging.getPosition().y);
        world.createJoint(rDef);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion,(mySimulation.getPosition().x-width/2f)*50f,(mySimulation.getPosition().y)*50f,width*50f,height*50f);


//       batch.draw(region,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }

}