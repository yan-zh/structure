package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.Flower;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class FlowerAndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.flowerID+ActConstants.mainCharacterID;

    public FlowerAndMainCharacter() {ActConstants.contactList.put(contactID,this);}

    @Override
    public void react(UserData userData1, UserData userData2) {
        if(ActConstants.skillGroups[2]!=null){
            if(userData1.contactId==ActConstants.flowerID){
                Flower flower = (Flower)ActConstants.publicInformation.get(userData1.nameInPublicInformation);
                flower.contact();

            }else{
                Flower flower = (Flower)ActConstants.publicInformation.get(userData2.nameInPublicInformation);
                flower.contact();

            }
        }

    }
}
