package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BulletAndMain implements ContactReaction {

    public static long contactID1 = ActConstants.mainCharacterID+ActConstants.iceBulletID;
    public static long contactID2 = ActConstants.mainCharacterID+ActConstants.woodBulletID;
    public static long contactID3 = ActConstants.mainCharacterID+ActConstants.windBulletID;
    public static long contactID4 = ActConstants.mainCharacterID+ActConstants.sandBulletID;

    public BulletAndMain() {
        ActConstants.contactList.put(contactID1,this);
        ActConstants.contactList.put(contactID2,this);
        ActConstants.contactList.put(contactID3,this);
        ActConstants.contactList.put(contactID4,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        if (userData1.contactId == ActConstants.mainCharacterID) {
            BulletSkill bulletSkill = (BulletSkill)ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            ActConstants.mainCharacter.damage(bulletSkill.getDamage());

        } else {
            BulletSkill bulletSkill = (BulletSkill)ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            ActConstants.mainCharacter.damage(bulletSkill.getDamage());
        }

    }
}
