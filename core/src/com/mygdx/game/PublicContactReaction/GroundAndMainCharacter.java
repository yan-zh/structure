package com.mygdx.game.PublicContactReaction;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class GroundAndMainCharacter implements ContactReaction {

    public static long contactID = 0b11;

    public GroundAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        ActConstants.mainCharacter.reFreshJump();

    }

}
