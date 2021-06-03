package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BubbleAndCharacter implements ContactReaction {
    public static long contactID= ActConstants.BubbleID+ActConstants.mainCharacterID;
    public BubbleAndCharacter(){
        ActConstants.contactList.put(contactID,this);
    }
    @Override
    public void react(UserData userData1, UserData userData2){
        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.MainCharacterState.replace("repulse",false);

    }

}
