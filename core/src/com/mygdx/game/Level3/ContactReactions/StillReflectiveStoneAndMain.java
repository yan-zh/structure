package com.mygdx.game.Level3.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.PhysicalAction;
import com.mygdx.game.abstraction.UserData;

public class StillReflectiveStoneAndMain implements ContactReaction {


    public static long contactID = ActConstants.stillReflectiveStoneID + ActConstants.mainCharacterID;

    String name;

    public StillReflectiveStoneAndMain()
    {
        ActConstants.contactList.put(contactID, this);

    }


    @Override
    public void react(UserData userData1, UserData userData2) {
        ActConstants.mainCharacter.reFreshJump();
    }
}
