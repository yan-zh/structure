package com.mygdx.game.Level3.Physicalactions;

import com.mygdx.game.Level3.NormalActors.BallLauncher;
import com.mygdx.game.abstraction.PhysicalAction;

public class CreateBall implements PhysicalAction {

    BallLauncher ballLauncher;

    public CreateBall(BallLauncher ballLauncher){
        this.ballLauncher = ballLauncher;
    }

    @Override
    public void act() {
        ballLauncher.launch();
    }
}

