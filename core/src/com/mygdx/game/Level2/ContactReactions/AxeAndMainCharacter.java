package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.Axe;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.NormalActors.ReverberateAxe;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class AxeAndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.axeID+ActConstants.mainCharacterID;

    public AxeAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        if (userData1.contactId == ActConstants.axeID) {
            ReverberateAxe axe = (ReverberateAxe) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            mainCharacter.die();
        } else {
            ReverberateAxe axe = (ReverberateAxe) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            mainCharacter.die();
        }

    }
}
