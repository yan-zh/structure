package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class Boss1AndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.Boss1ID+ActConstants.mainCharacterID;

    public Boss1AndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        System.out.println("ger pi le");
    }
}
