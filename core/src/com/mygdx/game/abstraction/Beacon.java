
package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsUI;

public class Beacon extends Actor {
    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    Animation wait;
    Animation set;

    TextureRegion currentFrame;

    public boolean state = true;

    float statetime;

    String name;

    World world;

    private int health;

    public Beacon(int x, int y, long actorId, World world, String name)
    {
        //Set position
        this.setX(x * ActConstants.worldSize_pAndPhysic);
        this.setY(y * ActConstants.worldSize_pAndPhysic);

        //Set the animation of the wait state and absorb
        this.wait = AssetsUI.instance.decoration.animXb;
        this.set = AssetsUI.instance.decoration.animXb;

        //Create the physical Entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myBodyDef.type = BodyDef.BodyType.StaticBody;
        myFixtureDef = PhysicalEntityDefine.getFd();
        myFixtureDef.isSensor = true;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);

        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        mySimulation.setGravityScale(1);
        myFixture = mySimulation.createFixture(myFixtureDef);

        myFixture.setUserData(new UserData(actorId, name));

        this.name = name;
        ActConstants.publicInformation.put(name, this);

        this.health = ActConstants.health;

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;

        if(state == true)  currentFrame = (TextureRegion) wait.getKeyFrame(statetime, true);
        else currentFrame = (TextureRegion) set.getKeyFrame(statetime, true);

    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(currentFrame, (mySimulation.getPosition().x - 3.5f)* 50f, (mySimulation.getPosition().y - 3f)*50f);

    }


    //To save the game state
    public void setBeacon()
    {
        MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));
        if(mainCharacter.mySimulation.getLinearVelocity().equals( new Vector2(0,0)))
        {
            this.state = false;

            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    Beacon beacon = (Beacon) ActConstants.publicInformation.get("Beacon");
                    beacon.state = true;

                }
            });

            Action action = Actions.delay(0.5f, delayedAction);
            this.addAction(action);

            this.mySimulation.setTransform(new Vector2(mainCharacter.mySimulation.getPosition().x , mainCharacter.mySimulation.getPosition().y), 0);
            this.health = ActConstants.health;
            AudioManager.instance.play(AssetsUI.instance.sounds.beacon);
            System.out.println("The game is saved");
        }

        else
            System.out.println("The area is dangerous" + mainCharacter.mySimulation.getLinearVelocity());


    }


    //To return the game the state
    public float[] returnState()
    {
        float State[] = new float[3];
        State[0] = this.mySimulation.getPosition().x;
        State[1] = this.mySimulation.getPosition().y;
        State[2] = this.health;
        return State;
    }
}
