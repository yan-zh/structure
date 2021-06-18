package com.mygdx.game.Level1.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.MainCharacter;
import com.mygdx.game.Level2.NormalActors.brokenBridge;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BridgeAndMainCharacter implements ContactReaction {
    public static long contactID = ActConstants.brokenBridgeID + ActConstants.mainCharacterID;
    private int stateJudge = 0;
    public BridgeAndMainCharacter()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        final UserData userData;
        if(userData1.contactId == ActConstants.brokenBridgeID){
            userData = userData1;
        }
        else userData = userData2;
        System.out.println(userData.nameInPublicInformation);
        brokenBridge brokenBridge = ((brokenBridge)ActConstants.publicInformation.get(userData.nameInPublicInformation));

        MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));

        System.out.println(brokenBridge.state + "this");

        if(userData.nameInPublicInformation == "brokenBridge")
        {
            //Refresh the state of jump
            ActConstants.mainCharacter.reFreshJump();
            if(brokenBridge != null && mainCharacter.mySimulation.getLinearVelocity().y<0)
            {
                AudioManager.instance.play(AssetsLevel0.instance.sounds.wood_Broken);

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
        if((userData.nameInPublicInformation) == "brokenDoor")
        {
            brokenBridge.removeBody();
            brokenBridge.state = false;
            System.out.println("THis is triggered");
            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    ((brokenBridge)ActConstants.publicInformation.get("brokenDoor")).state = true;
                    ((brokenBridge)ActConstants.publicInformation.get("brokenDoor")).remove();
                }
            });
            Action action = Actions.delay(0.5f, delayedAction);
            brokenBridge.addAction(action);
        }
    }
}
