package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.DelateBody;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.PhysicalEntityDefine;

public class Fairy extends Actor {

    public SkillGroup skillGroup;
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

    //输入的xy单位是像素
    public Fairy(SkillGroup skillGroup,int numberPosition, Animation animationWait, Animation animationAbsorb, int x, int y, long actorId, World world,String name) {
        this.skillGroup = skillGroup;
        this.numberPosition = numberPosition;

        state=true;

        this.setX(x);
        this.setY(y);

        //这里把wait的动画和draw的动画准备好
        this.wait = animationWait;
        this.absorb = animationAbsorb;

        //物理实体创建
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myFixtureDef = PhysicalEntityDefine.getFd();

        PolygonShape shape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;

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

        batch.draw(currentFrame,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);

    }

    @Override
    public boolean remove() {
        ActConstants.publicInformation.remove(name);
        return super.remove();
    }

    public void removeBody(){
        ActConstants.physicalActionList.add(new DelateBody(mySimulation,world));
    }
}
