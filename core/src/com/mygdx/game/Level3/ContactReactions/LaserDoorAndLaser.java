package com.mygdx.game.Level3.ContactReactions;

import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.laserTransmitter;
import com.mygdx.game.Level3.NormalActors.LaserDoor;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class LaserDoorAndLaser implements ContactReaction {


    public static long contactID = ActConstants.laserID+ActConstants.LaserDoorID;

    public LaserDoorAndLaser() {
        ActConstants.contactList.put(contactID,this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {


        if(userData1.contactId==ActConstants.LaserDoorID){
            LaserDoor laserDoor = (LaserDoor) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            laserDoor.remove = true;
        }else{
            LaserDoor laserDoor = (LaserDoor) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            laserDoor.remove = true;
        }

        laserTransmitter transmitter = (laserTransmitter) ActConstants.publicInformation.get("laserTransmitter");


    }
}
