package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.abstraction.PhysicalAction;

public class AdjustPosition implements PhysicalAction {

    float x;
    float y;

    public AdjustPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void act() {
        MainCharacter mainCharacter = ((MainCharacter) ActConstants.publicInformation.get("MainCharacter"));
        mainCharacter.mySimulation.setTransform(new Vector2(x*50,y*50), 0);

    }
}
