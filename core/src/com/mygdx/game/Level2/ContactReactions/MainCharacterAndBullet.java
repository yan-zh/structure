package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class MainCharacterAndBullet implements ContactReaction {

    public static long contactID1 = ActConstants.mainCharacterID+ActConstants.iceBulletID;
    public static long contactID2 = ActConstants.mainCharacterID+ActConstants.woodBulletID;
    public static long contactID3 = ActConstants.mainCharacterID+ActConstants.windBulletID;
    public static long contactID4 = ActConstants.mainCharacterID+ActConstants.sandBulletID;

    public MainCharacterAndBullet() {
        ActConstants.contactList.put(contactID1,this);
        ActConstants.contactList.put(contactID2,this);
        ActConstants.contactList.put(contactID3,this);
        ActConstants.contactList.put(contactID4,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        if (userData1.contactId == ActConstants.mainCharacterID) {


            System.out.println("dsddd111");
            MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            BulletSkill bulletSkill = (BulletSkill) ActConstants.publicInformation.get(userData2.nameInPublicInformation);



            boolean isDead = AssetsUI.instance.reduceLives(bulletSkill.getDamage());

            if (isDead==true) {
                mainCharacter.die();
            }

//
//            boolean isAlive = mainCharacter.damage(bulletSkill.getDamage());
//            if (isAlive) {
//                mainCharacter.die();
//            }


        } else {

            MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            BulletSkill bulletSkill = (BulletSkill) ActConstants.publicInformation.get(userData1.nameInPublicInformation);

            System.out.println("dsddd222");

            boolean isAlive = AssetsUI.instance.reduceLives(bulletSkill.getDamage());

            if (isAlive==true) {
                mainCharacter.die();
            }

//
//            boolean isAlive = mainCharacter.damage(bulletSkill.getDamage());
//            if (isAlive) {
//                mainCharacter.die();
//            }

        }
    }


}
