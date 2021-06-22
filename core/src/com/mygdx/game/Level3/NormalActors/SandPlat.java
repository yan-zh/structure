package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class SandPlat extends Actor {
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    TextureRegion wait;
    Animation broken;

    TextureRegion currentFrame;

    public boolean state = true;

    float statetime;

    String name;

    World world;

    public SandPlat(TextureRegion animationWait, Animation animationAbsorb, float x, float y, long actorId, World world, String name)
    {
        //Set position
        this.setX(x * ActConstants.worldSize_pAndPhysic);
        this.setY(y * ActConstants.worldSize_pAndPhysic);

        //Set the animation of the wait state and absorb
        this.wait = animationWait;
        this.broken = animationAbsorb;

        //Create the physical Entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myBodyDef.type = BodyDef.BodyType.StaticBody;
        myFixtureDef = PhysicalEntityDefine.getFd();
        myFixtureDef.friction = 1;
        myFixtureDef.isSensor = false;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(6f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);

        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        mySimulation.setGravityScale(1);
        myFixture = mySimulation.createFixture(myFixtureDef);

        myFixture.setUserData(new UserData(actorId, name));

        this.name = name;
        ActConstants.publicInformation.put(name, this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

       currentFrame = wait;

//        else currentFrame = (TextureRegion) broken.getKeyFrame(statetime, false);

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(state == true)  batch.draw(currentFrame, (mySimulation.getPosition().x - 5f)* 50f, (mySimulation.getPosition().y - 3.5f)*50f);
    }



    //To remove the physical body
    public void removeBody()
    {
     this.state = false;
     this.myFixture.setSensor(true);
    }

    public void createBody()
    {
        this.state = true;
        this.myFixture.setSensor(false);
    }

}
