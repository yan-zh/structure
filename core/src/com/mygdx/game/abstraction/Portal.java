package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.UserData;

public class Portal extends Actor{
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    TextureRegion wait;
    Animation trigger;

    TextureRegion currentFrame;

    public boolean state = true;

    public boolean triggerState = false;

    float statetime;

    public String name;

    public String Stagetranto;

    public Boolean isActive;

    public int dstXPos, dstYPos;

    World world;

    public Portal(int x, int y, int dstX, int dstY, long actorId, World world, String name, Boolean isActive, String Stagetranto)
    {
        this.Stagetranto = Stagetranto;
        this.isActive = isActive;
        //Set position
        this.setX(x);
        this.setY(y+40);

        //Set the destination position
        this.dstXPos = dstX;
        this.dstYPos = dstY;
        //Set the animation of the wait state and absorb
        this.wait = AssetsUI.instance.decoration.chuansong;
        this.trigger = AssetsUI.instance.decoration.animChuansong;
        //Create the physical Entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        //这里设定盒子的大小
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5f/ ActConstants.worldSize_shapeAndPhysics,5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);
        myBodyDef.type = BodyDef.BodyType.StaticBody;
        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        myFixture = mySimulation.createFixture(myFixtureDef);
        myFixture.setSensor(true);
        myFixture.setUserData(new UserData(actorId, name));

        this.name = name;
        ActConstants.publicInformation.put(name, this);
        currentFrame = AssetsUI.instance.decoration.chuansong;


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame, (mySimulation.getPosition().x - 3.5f)* 50f, (mySimulation.getPosition().y - 3f)*50f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        if(state == true) currentFrame = wait;

        else currentFrame = (TextureRegion) trigger.getKeyFrame(statetime, true);
    }

    public void turnOn()
    {
        this.isActive = true;
    }

    public void turnOff()
    {
        this.isActive = false;
    }

}
