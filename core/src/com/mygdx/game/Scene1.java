package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.BitSet;

public class Scene1 {//每个人写自己的scene，创建关卡中的实体(actor)

    FixtureDef fixtureDef;
    BodyDef bodyDef;
    PolygonShape shape;

    public Scene1(World world, Stage stage){
        PhysicalEntityDefine.defineWall();



        //地面
        PhysicalEntityDefine.defineWall();

        bodyDef = PhysicalEntityDefine.getBd();
        shape = new PolygonShape();
        fixtureDef = PhysicalEntityDefine.getFd();

        shape.setAsBox(3000/ PublicData.worldSize_shapeAndPhysics,2/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
        bodyDef.position.set(1400,1);//物体以中心作为自己的坐标，表示物理世界中的米

        fixtureDef.shape = shape;

        Body floor = world.createBody(bodyDef);
        floor.createFixture(fixtureDef).setUserData("ground");//后面的setuserdata可以是任意的object，这里放文本是为了在碰撞时区分不同的刚体


        //实体块

//        PhysicalEntityDefine.defineWall();
//        bodyDef = PhysicalEntityDefine.getBd();
//        shape = new PolygonShape();
//        fixtureDef = PhysicalEntityDefine.getFd();
//
//        shape.setAsBox(30/ PublicData.worldSize_shapeAndPhysics,2.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(5,3.3f);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        fixtureDef.shape = shape;
//
//        Body block1 = world.createBody(bodyDef);
//        block1.createFixture(fixtureDef).setUserData("block");
//
//        //地面薄膜
//        shape.setAsBox(30/ PublicData.worldSize_shapeAndPhysics,0.1f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(5,4.5f);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        fixtureDef.shape = shape;
//
//        Body groundf1 = world.createBody(bodyDef);
//        groundf1.createFixture(fixtureDef).setUserData("ground");
//
//
//        shape.setAsBox(15/ PublicData.worldSize_shapeAndPhysics,2.5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(-2.5f,5.7f);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        Body block2 = world.createBody(bodyDef);
//        block2.createFixture(fixtureDef).setUserData("block");
//        System.out.println(block2.getPosition());
//
//        shape.setAsBox(15/ PublicData.worldSize_shapeAndPhysics,0.1f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(-2.5f,7f);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        Body groundf2 = world.createBody(bodyDef);
//        groundf2.createFixture(fixtureDef).setUserData("ground");
//
//
//        shape.setAsBox(8/ PublicData.worldSize_shapeAndPhysics,5f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(-3f,9.5f);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        Body block3 = world.createBody(bodyDef);
//        block3.createFixture(fixtureDef).setUserData("block");
//
//
//        shape.setAsBox(16/ PublicData.worldSize_shapeAndPhysics,1f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(-3f,12.5f);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        Body block4 = world.createBody(bodyDef);
//        block4.createFixture(fixtureDef).setUserData("block");
//
//        shape.setAsBox(16/ PublicData.worldSize_shapeAndPhysics,0.1f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(-3f,13f);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        Body groundf4 = world.createBody(bodyDef);
//        groundf4.createFixture(fixtureDef).setUserData("ground");




        //可攀爬
//        shape.setAsBox(1/ PublicData.worldSize_shapeAndPhysics,10f/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(11f,23f);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        Body ground1 = world.createBody(bodyDef);
//        ground1.createFixture(fixtureDef).setUserData("ground");





        //阶梯
//        PhysicalEntityDefine.defineWall();
//        bodyDef = PhysicalEntityDefine.getBd();
//        shape = new PolygonShape();
//        fixtureDef = PhysicalEntityDefine.getFd();
//
//        shape.setAsBox(5/ PublicData.worldSize_shapeAndPhysics,2/ PublicData.worldSize_shapeAndPhysics);//worldsize左边的数值表示物理世界中的米
//        bodyDef.position.set(5,5);//物体以中心作为自己的坐标，表示物理世界中的米
//
//        fixtureDef.shape = shape;

       // Body stair1 = world.createBody(bodyDef);
       // stair1.createFixture(fixtureDef).setUserData("ground");



//        bodyDef.position.set(10,10);//物体以中心作为自己的坐标，表示物理世界中的米
//        Body stair2 = world.createBody(bodyDef);
//        stair2.createFixture(fixtureDef).setUserData("back");
        //System.out.println(fd.filter.categoryBits);
        //.out.println(fd.filter.maskBits);

        shape.setAsBox(1/PublicData.worldSize_shapeAndPhysics,1/PublicData.worldSize_shapeAndPhysics);
        fixtureDef.shape = shape;
        bodyDef.position.set(0f,0);
        Body ruler1 = world.createBody(bodyDef);
        ruler1.createFixture(fixtureDef).setUserData("ground");

//        bodyDef.position.set(11,5);
//        Body ruler2 = world.createBody(bodyDef);
//        ruler2.createFixture(fixtureDef).setUserData("ground");
    }
}
