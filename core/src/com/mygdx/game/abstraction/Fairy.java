package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsUI;

public class Fairy extends Actor {

    public int numberPosition;

    Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    Animation wait;
    Animation absorb;

    TextureRegion currentFrame;

    public boolean state;//true就是存在

    float statetime;

    public int NumberPosition;

    World world;

    String name;

    float physicalX;
    float physicalY;

    //x y pixel
    public Fairy(int numberPosition, Animation animationWait, Animation animationAbsorb, int x, int y, long actorId, World world,String name) {

        this.numberPosition = numberPosition;

        state=true;

        this.setX(x);
        this.setY(y);
        this.physicalX = x/50;
        this.physicalY = y/50;

        //prepare different Animation
        this.wait = animationWait;
        this.absorb = animationAbsorb;

        //curete entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2.2f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor = false;

        myBodyDef.position.set(this.getX()/ActConstants.worldSize_pAndPhysic,this.getY()/ActConstants.worldSize_pAndPhysic);//这个表示物理世界中的米

        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        myFixture = mySimulation.createFixture(myFixtureDef);
        myFixture.setUserData(new UserData(actorId,name));


        this.name = name;
        ActConstants.publicInformation.put(name,this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;//used to play animation, the time

        if(state==true)currentFrame = (TextureRegion)wait.getKeyFrame(statetime,true);
        else currentFrame = (TextureRegion)absorb.getKeyFrame(statetime,true);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        //face to main character
        if(ActConstants.mainCharacter.getPhysicalX()>=this.physicalX) {
            if (!currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }else{
            if (currentFrame.isFlipX()) {
                currentFrame.flip(true, false);
            }
        }

        batch.draw(currentFrame,(mySimulation.getPosition().x-1.1f)*50f, (mySimulation.getPosition().y-0.75f)*50f);

    }

    @Override
    public boolean remove() {
        ActConstants.publicInformation.remove(name);
        return super.remove();
    }

    //remove physical entity
    public void removeBody(){
        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(mySimulation,world);
        ActConstants.physicalActionList.add(deletePhysicalEntity);

        AudioManager.instance.play(AssetsUI.instance.sounds.fiary_Absorb);
    }
}
