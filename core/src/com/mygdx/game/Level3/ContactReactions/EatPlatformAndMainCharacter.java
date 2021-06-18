package com.mygdx.game.Level3.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level3.NormalActors.EatPlatform;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class EatPlatformAndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.eatPlatformID+ActConstants.mainCharacterID;

    public EatPlatformAndMainCharacter() {ActConstants.contactList.put(contactID,this);}

    @Override
    public void react(UserData userData1, UserData userData2) {
        ActConstants.mainCharacter.reFreshJump();

        if(userData1.contactId==ActConstants.eatPlatformID){
            ((EatPlatform)ActConstants.publicInformation.get(userData1.nameInPublicInformation)).contact();
        }else{
            ((EatPlatform)ActConstants.publicInformation.get(userData2.nameInPublicInformation)).contact();
        }



    }
}
