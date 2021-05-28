package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureArray;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.UserData;

public class Ice extends Actor {
    public Body mySimulation;
    FixtureDef myFixtureDef;
    BodyDef myBodyDef;
    Fixture myFixture;

    Image image;

    public boolean state = true;

    String name;

    World world;

    public Ice(int x, int y, long actorId, World world, String name)
    {
        //Set position
        this.setX(x);
        this.setY(y);
        image = new Image(new Texture("D:\\structurenew\\core\\assets\\images\\anim_bunny_copter_01.png"));
        image.setSize(1.0f, 1.0f);
        image.setOrigin(image.getWidth() / 2.0f, image.getHeight() / 2.0f);
        //Create the physical Entity
        PhysicalEntityDefine.defineCharacter();
        myBodyDef = PhysicalEntityDefine.getBd();
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        myFixtureDef = PhysicalEntityDefine.getFd();



        //这里设定盒子的大小
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f/ ActConstants.worldSize_shapeAndPhysics,1.5f/ ActConstants.worldSize_shapeAndPhysics);
        myFixtureDef.shape = shape;


        myBodyDef.position.set(this.getX() / ActConstants.worldSize_pAndPhysic, this.getY() / ActConstants.worldSize_pAndPhysic);

        this.world = world;

        mySimulation = world.createBody(myBodyDef);
        myFixture = mySimulation.createFixture(myFixtureDef);
        mySimulation.setFixedRotation(false);
        mySimulation.setUserData(image);
        myFixture.setUserData(new UserData(actorId, "ice"));

        this.name = name;
        ActConstants.publicInformation.put(name, this);

    }

    @Override
    public void act(float delta) {
        super.act(delta);



    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion textureRegion = new TextureRegion(new Texture("images/anim_bunny_copter_01.png"));

        batch.draw(textureRegion, (mySimulation.getPosition().x - 0.7f)* 50f, (mySimulation.getPosition().y - 0.45f)*50f,(float)0.5*textureRegion.getRegionWidth(),(float)0.5*textureRegion.getRegionHeight(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),(float) 1.0,(float) 1.0, (float) (180*mySimulation.getAngle()/3.14),true);

    }
}
