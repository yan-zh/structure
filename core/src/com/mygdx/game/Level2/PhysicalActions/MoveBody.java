package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.abstraction.PhysicalAction;
import sun.rmi.rmic.Main;

public class MoveBody implements PhysicalAction {

    MainCharacter mainCharacter;

    public MoveBody(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    @Override
    public void act() {

    }
}
