package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level3.NormalActors.ReflectiveStone;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.abstraction.PhysicalAction;

public class CreateStone implements PhysicalAction {


    World world;

    public CreateStone(World world) {
        this.world = world;
    }

    @Override
    public void act() {

        MainCharacter mainCharacter = ActConstants.mainCharacter;

        MyGdxGame.currentStage.addActor(new  ReflectiveStone((int) (mainCharacter.getX()-100), (int) mainCharacter.getY(), ActConstants.ReflectiveStoneID,world,3));
    }
}
