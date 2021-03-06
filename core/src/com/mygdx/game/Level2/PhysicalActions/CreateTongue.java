package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.abstraction.PhysicalAction;
import com.mygdx.game.abstraction.UserData;

public class CreateTongue implements PhysicalAction {


    World world;

    Body tongueSimulation;
    FixtureDef tongueFixtureDef;
    BodyDef tongueBodyDef;
    PolygonShape tongueShape;

    float physicalX;
    float physicalY;

    public CreateTongue(World world, Body tongueSimulation, FixtureDef tongueFixtureDef, BodyDef tongueBodyDef, PolygonShape tongueShape, float physicalX, float physicalY) {
        this.world = world;
        this.tongueSimulation = tongueSimulation;
        this.tongueFixtureDef = tongueFixtureDef;
        this.tongueBodyDef = tongueBodyDef;
        this.tongueShape = tongueShape;
        this.physicalX = physicalX;
        this.physicalY = physicalY;
    }

    @Override
    public void act() {
        //创建舌头物理模拟
        PhysicalEntityDefine.defineStatic();
        tongueBodyDef = PhysicalEntityDefine.getBd();
        tongueFixtureDef = PhysicalEntityDefine.getFd();



        tongueShape = new PolygonShape();
        // shape.setRadius(1.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数表示物理世界中的米
        tongueShape.setAsBox(3f/ ActConstants.worldSize_shapeAndPhysics,0.5f/ ActConstants.worldSize_shapeAndPhysics);
        tongueFixtureDef.shape = tongueShape;

        tongueFixtureDef.isSensor = false;

        tongueBodyDef.position.set(physicalX-1,physicalY-1);//这个表示物理世界中的米

        tongueSimulation = world.createBody(tongueBodyDef);
        //mySimulation.createFixture(myFixtureDef).setUserData("main character");
        tongueSimulation.createFixture(tongueFixtureDef).setUserData(new UserData(ActConstants.groundID,"Ground"));

    }
}
