package com.mygdx.game.Level3.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.BodyBuilder;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.abstraction.BulletSkill;

public class Boss extends Actor {
    World world;
    String name;
    float width;
    float height;
    long actorId;
    Body body;

    float range;//每次移动距离基数
    float duration;
    Timer timer;
    Timer.Task timerTask;

    boolean fire;

    public Boss(World world, float x, float y,float width,float height,long actorId,String name) {

        fire=false;

        this.world = world;
        this.name=name;
        setX(x);
        setY(y);
        this.width=width;
        this.height=height;
        this.actorId=actorId;
        createBoss();
        ActConstants.publicInformation.put(name,this);
        range=5f;
        duration=0.5f;

        timer = new Timer();
        timerTask = new Timer.Task() {
            @Override

            public void run() {
                move();

            }

        };
        start();
    }

    private void createBoss(){
        Body body = BodyBuilder.createKineticBox(world, getX(), getY(), width, height, actorId, name);
        this.body=body;
    }

    private void start(){
        timer.scheduleTask(timerTask, 0, 3, 100);

    }



    private void patrol(){
        //boss可以以很快的速度移动，每次移动range+-random距离后停下几秒(然后发射子弹)；每次随机往左或往右移动，如果x<x1或x>x2则往中间移动

    }

    private void move(){
        float distance=range+ MathUtils.random(-1f,1f);
        distance=distance*MathUtils.randomSign();
        MoveByAction action= Actions.moveBy(distance,0,duration);
        this.addAction(action);
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        body.setTransform(new Vector2(getX(),getY()),0);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        batch.draw(region,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }


}
