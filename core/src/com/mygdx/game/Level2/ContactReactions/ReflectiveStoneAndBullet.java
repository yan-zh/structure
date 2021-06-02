package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.NormalActors.Portal;
import com.mygdx.game.Level2.NormalActors.ReflectiveStone;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class ReflectiveStoneAndBullet implements ContactReaction {
    public static long contactID = ActConstants.ReflectiveStoneID + ActConstants.iceBulletID;
    public ReflectiveStoneAndBullet()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {
       System.out.println("The ReflectiveStone is triggered");
       ReflectiveStone reflectiveStone = ((ReflectiveStone)ActConstants.publicInformation.get("ReflectiveStone"));
       reflectiveStone.emmit();

    }
}
