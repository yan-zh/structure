package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class SensorAndMainCharacter implements ContactReaction {
    public static long contactID = ActConstants.SensorID+ActConstants.mainCharacterID;

    public SensorAndMainCharacter()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {

    }
}
