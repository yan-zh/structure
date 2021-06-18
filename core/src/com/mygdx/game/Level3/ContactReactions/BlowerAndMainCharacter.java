package com.mygdx.game.Level3.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BlowerAndMainCharacter implements ContactReaction {


    public static long contactID = ActConstants.blowerID+ActConstants.mainCharacterID;

    public BlowerAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        ActConstants.MainCharacterState.replace("blow",true);

    }
}
