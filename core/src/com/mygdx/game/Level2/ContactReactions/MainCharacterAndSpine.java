package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.NormalActors.Spine;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class MainCharacterAndSpine implements ContactReaction {
    public static long contactID = ActConstants.spineID+1;

    public MainCharacterAndSpine() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get("MainCharacter");
        Spine spine;

        if(userData1.contactId==ActConstants.spineID){
            spine = (Spine)ActConstants.publicInformation.get(userData1.nameInPublicInformation);
        }else{
            spine = (Spine)ActConstants.publicInformation.get(userData2.nameInPublicInformation);
        }

        mainCharacter.repulse(spine.getPhysicalX(),spine.getPhysicalY());


    }
}
