package com.mygdx.game.Level2.PhysicalActions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.PhysicalAction;

public class ReverseMainCharacterGravity implements PhysicalAction {

    public ReverseMainCharacterGravity() {
    }

    @Override
    public void act() {
        if(ActConstants.canGravityInverse==true){
            ActConstants.mainCharacter.startGravityInverse();
        }

    }
}
