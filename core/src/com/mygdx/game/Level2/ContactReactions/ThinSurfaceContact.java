package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.NormalActors.ThinSurface;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class ThinSurfaceContact implements ContactReaction {

    public static long contactID = 0b1+0b1000000000000000000000000000000;

    public ThinSurfaceContact(){
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        //Refresh the state of jump
        ActConstants.MainCharacterState.replace("onGround",true);
       ActConstants.MainCharacterState.replace("repulse",false);
        ThinSurface srf= (ThinSurface) ActConstants.publicInformation.get("thinSurface");
        MainCharacter mainCha= (MainCharacter) ActConstants.publicInformation.get("MainCharacter");

    }



}
