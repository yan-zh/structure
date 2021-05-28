package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.Axe;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class AxeSensorContact implements ContactReaction {


    public static long contactID = ActConstants.axeSensorID+ActConstants.mainCharacterID;

    public AxeSensorContact() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        if (userData1.contactId == ActConstants.axeSensorID) {
            Axe axe = (Axe) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            axe.fall();
            if(axe!=null){
                axe.stop();
            }

        }else{
            Axe axe = (Axe) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            axe.fall();
            if(axe!=null){
                axe.stop();
            }
        }
    }
}
