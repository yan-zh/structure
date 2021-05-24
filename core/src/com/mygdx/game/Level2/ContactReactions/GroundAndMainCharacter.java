package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ContactReaction;

public class GroundAndMainCharacter implements ContactReaction {

    public static long contactID = 0x11;

    public GroundAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react() {
        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.MainCharacterState.replace("repulse",false);

    }


}
