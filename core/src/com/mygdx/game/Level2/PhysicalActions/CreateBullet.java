package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.PhysicalAction;
import com.mygdx.game.abstraction.UserData;

public class CreateBullet implements PhysicalAction {


    Animation prepare;
    Animation fly;
    Animation contact;
    float x;
    float y;
    float[] direction;
    long actorId;
    int damage;

    public CreateBullet(Animation prepare, Animation fly, Animation contact, float x, float y, float[] direction, long actorId, int damage) {
        this.prepare = prepare;
        this.fly = fly;
        this.contact = contact;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.actorId = actorId;
        this.damage = damage;
    }

    @Override
    public void act() {
        MyGdxGame.currentStage.addActor(new BulletSkill(Assets.instance.bunny.head,Assets.instance.bunny.getAnimCopterRotate,x,y,direction,actorId,damage));
    }
}
