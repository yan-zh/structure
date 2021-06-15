package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MonsterA;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class MonsterASensorAndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.monsterSensorID+ActConstants.mainCharacterID;

    public MonsterASensorAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        if(userData1.contactId==ActConstants.monsterSensorID){

            MonsterA monsterA = (MonsterA) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            if(monsterA!=null){
                monsterA.start();
            }
        }else{
            MonsterA monsterA = (MonsterA) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            if(monsterA!=null){
                monsterA.start();
            }
        }
    }
}
