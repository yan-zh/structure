package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.SkillGroupManager.SkillGourpFire;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.Fairy;

public class WindFairyAndMainCharacter implements ContactReaction {
    public static long contactID = 0b101;

    public WindFairyAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react() {
        if(((Fairy)ActConstants.publicInformation.get("WindFairy"))!=null){
            ActConstants.skillGroups[((Fairy)ActConstants.publicInformation.get("WindFairy")).numberPosition] = new SkillGourpFire();
            ((Fairy)ActConstants.publicInformation.get("WindFairy")).remove();
        }



    }
}
