package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.abstraction.Portal;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class PortalAndMainCharacter implements ContactReaction {
    public static long contactID = 0b10000001;
    public PortalAndMainCharacter()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        UserData userData;
        if(userData1.contactId == ActConstants.portalID)
            userData = userData1;
        else
            userData = userData2;
        Portal portal =((Portal)ActConstants.publicInformation.get(userData.nameInPublicInformation));
        ActConstants.currentPortal = userData.nameInPublicInformation;
        MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));
        if(portal != null && (Gdx.input.isKeyPressed(Input.Keys.K))== true)
        {
            System.out.println("The Portal is triggerred");

        }
    }
}
