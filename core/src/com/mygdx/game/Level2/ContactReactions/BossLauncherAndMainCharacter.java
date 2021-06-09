package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.BossLauncher;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BossLauncherAndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.bossLauncherID+ActConstants.mainCharacterID;

    public BossLauncherAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        if(userData1.contactId==ActConstants.bossLauncherID){
            BossLauncher bossLauncher = (BossLauncher) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            bossLauncher.stop();
        }else{
            BossLauncher bossLauncher = (BossLauncher) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            bossLauncher.stop();
        }
    }
}
