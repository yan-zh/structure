package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BearWindContact implements ContactReaction {

public static long contactID= ActConstants.BearID+ActConstants.windBulletID;//风技能还没做好，先用主角碰撞替代一下

public BearWindContact(){ActConstants.contactList.put(contactID,this);}

    @Override
    public void react(UserData userData1, UserData userData2) {
        //Refresh the state of jump
        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.MainCharacterState.replace("repulse",false);

    }
}