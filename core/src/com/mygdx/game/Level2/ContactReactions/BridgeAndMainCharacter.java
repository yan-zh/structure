package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.Level2.NormalActors.brokenBridge;

public class BridgeAndMainCharacter implements ContactReaction {
    public static long contactID = 0b1001;
    private int stateJudge = 0;
    public BridgeAndMainCharacter()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react() {
        brokenBridge brokenBridge = ((brokenBridge)ActConstants.publicInformation.get("brokenBridge"));
        MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));

        if(brokenBridge != null && mainCharacter.mySimulation.getLinearVelocity().y<0)
        {
            brokenBridge.removeBody();
            brokenBridge.state = false;

            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                ((brokenBridge)ActConstants.publicInformation.get("brokenBridge")).state = true;
                ((brokenBridge)ActConstants.publicInformation.get("brokenBridge")).remove();
                }
            });
            Action action = Actions.delay(0.5f, delayedAction);
            brokenBridge.addAction(action);
        }
    }
}
