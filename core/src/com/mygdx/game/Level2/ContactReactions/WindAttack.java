package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ApplySkill;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class WindAttack implements ContactReaction {
    public static long contactID = 0b10000;

    public WindAttack() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        if(userData1.contactId==ActConstants.windBulletID){
            ApplySkill applySkill = (ApplySkill) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            applySkill.contactMark=true;
            applySkill.flyMark=false;
            applySkill.deleteBody();

        }else{
            ApplySkill applySkill = (ApplySkill) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            applySkill.contactMark=true;
            applySkill.flyMark=false;
            applySkill.deleteBody();
        }
    }
}