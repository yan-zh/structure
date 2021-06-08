package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class Door extends Actor {
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;


    Animation wait;
    Animation trigger;

    TextureRegion currentFrame;

    public boolean state = true;

    public boolean triggerState = false;

    float statetime;

    String name;

    public int dstXPos, dstYPos;

    World world;
        public Door(Animation animationWait, Animation animationTrigger, float x, float y, long actorId, World world, String name)
    {
        //Set position
        this.setX(x * ActConstants.worldSize_pAndPhysic);
        this.setY(y * ActConstants.worldSize_pAndPhysic);

        //Set the animation of the wait state and absorb
        this.wait = animationWait;
        this.trigger = animationTrigger;

        //Create the physical Entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myBodyDef.type = BodyDef.BodyType.KinematicBody;
        myFixtureDef = PhysicalEntityDefine.getFd();

        //这里设定盒子的大小
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);

        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        mySimulation.setGravityScale(5);
        myFixture = mySimulation.createFixture(myFixtureDef);


        myFixture.setUserData(new UserData(actorId, "door"));

        this.name = name;
        ActConstants.publicInformation.put(name, this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        if(state == true) currentFrame = (TextureRegion)wait.getKeyFrame(statetime, true);

        else currentFrame = (TextureRegion) trigger.getKeyFrame(statetime, true);
        if(mySimulation.getPosition().y >= 600 / ActConstants.worldSize_pAndPhysic)
        {
            mySimulation.setLinearVelocity(new Vector2(0,0));
        }

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame, (mySimulation.getPosition().x - 0.7f)* 50f, (mySimulation.getPosition().y - 0.45f)*50f);

    }

    public void turnOn()
    {
        this.triggerState = true;
        System.out.println("The door is opened");
        mySimulation.setLinearVelocity(new Vector2(0,4));

        System.out.println(this.myBodyDef.type);
    }
}
