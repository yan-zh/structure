package com.mygdx.game.Level2.PhysicalActions;

import com.mygdx.game.Level2.NormalActors.BallLauncher;
import com.mygdx.game.Level2.NormalActors.ReboundBall;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
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

