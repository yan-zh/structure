package com.mygdx.game.Level2.NormalActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.BodyBuilder;

public class HangedBalls extends Actor {
    String password;
    String pswEntered="";
    int pswLength;
    int enteredLength;

    float x;
    float y;
    long actorId;

    World world;
    String name;


    public HangedBalls(World world, float x, float y,String password,long actorId,String name){
        setPassword(password);
        this.world=world;
        this.name=name;
       this.x=x;
       this.y=y;
       this.actorId=actorId;
       createBalls();
        ActConstants.publicInformation.put(name,this);
    }

    public void setPassword(String password) {
        this.password = password;
        this.pswLength=password.length();
    }

    public void createBalls(){
        Array<Body> bodies= new Array<Body>();
        Array<Body> vertices=new Array<Body>();
        Array<Vector2> positions=new Array<>();

        for (Integer i = 0; i < 3; i++) {//注意这里改坐标的话三行代码都得改
            vertices.add(BodyBuilder.createBox(world, x+i*2 , y+2f, 0.05f, 0.05f, false, 0b10, "vertex"));
            positions.add(new Vector2(x+i*2  , y+2f));
            bodies.add(BodyBuilder.createCircle(world, x+i*2 , y, 0.5f, true,
                    actorId, name+i.toString()));
            RevoluteJointDef rDef= new RevoluteJointDef();
            rDef.initialize(vertices.get(i),bodies.get(i),positions.get(i));
            rDef.enableLimit=true;
            rDef.lowerAngle=-(float)Math.PI/12;
            rDef.upperAngle=(float)Math.PI/12;
            world.createJoint(rDef);
        }


    }

    public void hit(String i){
        this.pswEntered+=i;
        this.enteredLength= this.pswEntered.length();
        if(enteredLength>=pswLength){
            if (password.equals(pswEntered.substring(enteredLength - pswLength))) {
                this.pswCorrect();
            }
        }
    }

    private void pswCorrect(){
        System.out.println("psw correct!");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

//        batch.draw(region,(mySimulation.getPosition().x-0.7f)*50f, (mySimulation.getPosition().y-0.45f)*50f);
    }

}
