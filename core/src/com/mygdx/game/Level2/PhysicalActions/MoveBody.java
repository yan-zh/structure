package com.mygdx.game.Level2.PhysicalActions;

import com.mygdx.game.abstraction.MainCharacter;
import com.mygdx.game.abstraction.PhysicalAction;

public class MoveBody implements PhysicalAction {

    MainCharacter mainCharacter;

    public MoveBody(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    @Override
    public void act() {

    }
}
