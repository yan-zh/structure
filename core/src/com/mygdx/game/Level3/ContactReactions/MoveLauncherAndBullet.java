package com.mygdx.game.Level3.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level3.NormalActors.MoveLauncher;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class MoveLauncherAndBullet implements ContactReaction {


    public static long contactID1 = ActConstants.moveLauncherID+ActConstants.woodBulletID;
    public static long contactID2 = ActConstants.moveLauncherID+ActConstants.windBulletID;
    public static long contactID3 = ActConstants.moveLauncherID+ActConstants.sandBulletID;


    public MoveLauncherAndBullet() {

        ActConstants.contactList.put(contactID1,this);
        ActConstants.contactList.put(contactID2,this);
        ActConstants.contactList.put(contactID3,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        int hurt;

        if(userData1.contactId==ActConstants.moveLauncherID){
            hurt = ((BulletSkill)ActConstants.publicInformation.get(userData2.nameInPublicInformation)).getDamage();
        }else{
            hurt = ((BulletSkill)ActConstants.publicInformation.get(userData1.nameInPublicInformation)).getDamage();
        }

        ((MoveLauncher)ActConstants.publicInformation.get("moveLauncher")).damage(hurt);

    }
}
