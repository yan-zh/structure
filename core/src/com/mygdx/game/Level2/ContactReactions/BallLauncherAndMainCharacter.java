package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.BallLauncher;
import com.mygdx.game.Level2.PhysicalActions.CreateBall;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BallLauncherAndMainCharacter implements ContactReaction {

    int number;

    public static long contactID = ActConstants.ballLauncherID+ActConstants.mainCharacterID;

    public BallLauncherAndMainCharacter() {
        number=0;
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        if(number==0){
            if(userData1.contactId==ActConstants.ballLauncherID){
                BallLauncher ballLauncher = (BallLauncher) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
                ballLauncher.launch=true;
            }else{
                BallLauncher ballLauncher = (BallLauncher) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
                ballLauncher.launch=true;
            }
        }

        number=1;

    }
}
