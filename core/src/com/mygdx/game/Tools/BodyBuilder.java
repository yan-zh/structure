package com.mygdx.game.Tools;

import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.abstraction.UserData;

//Author:lyq

public class BodyBuilder {

    static Body body;
    static Fixture fixture;

    public static Body createBox(World world, float x, float y, float width, float height, Boolean isKinetic,
                                 long actorId, String name      ) {
        BodyDef bodyDef = new BodyDef();
        if (isKinetic == true) {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        bodyDef.position.set(x, y);
        bodyDef.allowSleep = false; // set to true?
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f, height / 2f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 40;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new UserData(actorId, name));
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
//        System.out.println(name);
        return body;

    }

    public static Body createCircle(World world, float x, float y, float radius, Boolean isKinetic, long actorId, String name) {
        BodyDef bodyDef = new BodyDef();
        if (isKinetic == true) {
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        } else {
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        bodyDef.position.set(x, y);
        bodyDef.allowSleep = false; // set to true?
        body = world.createBody(bodyDef);
        CircleShape shape= new CircleShape();
        shape.setRadius(radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 40;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new UserData(actorId, name));
        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
        return body;

    }

}
