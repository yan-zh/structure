package com.mygdx.game.abstraction;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MonsterADie extends Actor {
    Animation die;
    TextureRegion currentFrame;

    float t;

    float pX;
    float pY;

    public MonsterADie(Animation die, float pX, float pY) {
        this.die = die;
        this.pX = pX;
        this.pY = pY;
        t=0;
        currentFrame =(TextureRegion) die.getKeyFrames()[0];
    }

    @Override
    public void act(float delta) {

        t += delta;
        System.out.println(t);
        System.out.println(delta);

        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        currentFrame = (TextureRegion) die.getKeyFrame(t,false);

        if(die.isAnimationFinished(t)){
            this.remove();
        }


        batch.draw(currentFrame,pX,pY);

    }
}
