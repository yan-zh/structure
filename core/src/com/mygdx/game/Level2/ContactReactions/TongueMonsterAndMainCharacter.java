package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level3.NormalActors.TongueMonster;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class TongueMonsterAndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.tongueMonsterID+ActConstants.mainCharacterID;

    public TongueMonsterAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        if(userData1.contactId==ActConstants.tongueMonsterID){
            TongueMonster tongueMonster = (TongueMonster) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            tongueMonster.setContact(true);
            MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            mainCharacter.reFreshJump();
        }else{
            TongueMonster tongueMonster = (TongueMonster) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            tongueMonster.setContact(true);
            MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            mainCharacter.reFreshJump();
        }


    }
}
