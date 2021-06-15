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

    //public SkillGroup skillGroup;
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

    //输入的xy单位是像素
    public Fairy(int numberPosition, Animation animationWait, Animation animationAbsorb, int x, int y, long actorId, World world,String name) {
       // this.skillGroup = skillGroup;
        this.numberPosition = numberPosition;

        state=true;

        this.setX(x);
        this.setY(y);
        this.physicalX = x/50;
        this.physicalY = y/50;

        //这里把wait的动画和draw的动画准备好
        this.wait = animationWait;
        this.absorb = animationAbsorb;

        //物理实体创建
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        PolygonShape shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(2.2f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

        myFixtureDef.isSensor = false;

        myBodyDef.position.set(this.getX()/ActConstants.worldSize_pAndPhysic,this.getY()/ActConstants.worldSize_pAndPhysic);//这个表示物理世界中的米

        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        myFixture = mySimulation.createFixture(myFixtureDef);
        myFixture.setUserData(new UserData(actorId,name));


        this.name = name;
        ActConstants.publicInformation.put(name,this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        statetime += delta;//用于调整主角要展示的图片的时间标记,****************这里调整了，用了传入的delta，看看行不行

        if(state==true)currentFrame = (TextureRegion)wait.getKeyFrame(statetime,true);
        else currentFrame = (TextureRegion)absorb.getKeyFrame(statetime,true);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

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

    public void removeBody(){
        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(mySimulation,world);
        ActConstants.physicalActionList.add(deletePhysicalEntity);

        AudioManager.instance.play(AssetsUI.instance.sounds.fiary_Absorb);
    }
}
