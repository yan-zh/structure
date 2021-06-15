package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.Portal;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.PhysicalEntityDefine;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.MyStage;
import com.mygdx.game.abstraction.PhysicalAction;
import com.mygdx.game.abstraction.UserData;

public class CreatePortal implements PhysicalAction {



    public CreatePortal() {

    }

    @Override
    public void act() {
        AssetsUI.instance.instance.init(new AssetManager());
        Stage2 stage2 = ((Stage2) ActConstants.publicInformation.get("stage2"));
        stage2.addActor(new Portal(10750,2800,40,60,ActConstants.portalID, ((MyStage)MyGdxGame.currentStage).world,"Portal2",true,"none"));




    }
}
