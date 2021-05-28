package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.*;

//Author:lyq

public class BodyBuilder {

    static Body body;

    public static Body createBox(World world, float x, float y,float width, float height,Boolean isKinetic ){
        BodyDef bodyDef = new BodyDef();
        if(isKinetic==true){
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }
        else{
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        bodyDef.position.set(x, y);
        bodyDef.active=true;
        bodyDef.allowSleep=false;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2f,height/2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 40;
        Fixture fixture = body.createFixture(fixtureDef);
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
        return body;


    }
}
