package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class rotateSwitch extends Actor {
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    TextureRegion wait;
    TextureRegion trigger;

    TextureRegion currentFrame;

    public boolean state = true;
    public boolean triggerState = false;
    float statetime;

    String name;

    public String ControlType;
    World world;



    public rotateSwitch(TextureRegion wait, TextureRegion trigger, float x, float y, long actorId, World world, String name, String controlType)
    {
        this.ControlType = controlType;
        //Set position
        this.setX(x * ActConstants.worldSize_pAndPhysic);
        this.setY(y * ActConstants.worldSize_pAndPhysic);

        //Set the animation of the wait state and absorb
        this.wait = wait;
        this.trigger = trigger;
        //Create the physical Entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);
        myBodyDef.type = BodyDef.BodyType.StaticBody;
        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        mySimulation.setGravityScale(5);
        myFixture = mySimulation.createFixture(myFixtureDef);
        myFixture.setSensor(true);
        myFixture.setUserData(new UserData(actorId, name));

        this.name = name;
        ActConstants.publicInformation.put(name, this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        if(state == true) currentFrame = wait;

        else currentFrame = trigger;

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame, (mySimulation.getPosition().x - 0.7f)* 50f, (mySimulation.getPosition().y - 0.45f)*50f);

    }
}
