package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.Ice;
import com.mygdx.game.Level2.NormalActors.brokenBridge;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BridgeAndIce implements ContactReaction {
    public static long contactID = 0b100000000000000001000;

    public BridgeAndIce()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        brokenBridge brokenBridge = ((brokenBridge)ActConstants.publicInformation.get("brokenBridge"));
        Ice ice = ((Ice)ActConstants.publicInformation.get("ice"));

        if(brokenBridge != null )
        {
            System.out.println("ice.mySimulation.getLinearVelocity().y");
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
