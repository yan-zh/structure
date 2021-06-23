package com.mygdx.game.PublicContactReaction;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BulletDispose implements ContactReaction {
    public static long contactID1 = ActConstants.windBulletID;
    public static long contactID2 = ActConstants.woodBulletID;
    public static long contactID3 = ActConstants.sandBulletID;
    public static long contactID4 = ActConstants.iceBulletID;
    public static long contactID5 = ActConstants.laserID;



    public BulletDispose() {
        ActConstants.contactList.put(contactID1,this);
        ActConstants.contactList.put(contactID2,this);
        ActConstants.contactList.put(contactID3,this);
        ActConstants.contactList.put(contactID4,this);
        ActConstants.contactList.put(contactID5,this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        if(userData1.contactId!=ActConstants.monsterSensorID&&userData2.contactId!=ActConstants.monsterSensorID&&userData1.contactId!=ActConstants.blowerID&&userData2.contactId!=ActConstants.blowerID){

            if(userData1.contactId==ActConstants.windBulletID||userData2.contactId==ActConstants.windBulletID){
                AudioManager.instance.play(AssetsUI.instance.sounds.bullet_Wind_Hit);
            }else if(userData1.contactId==ActConstants.woodBulletID||userData2.contactId==ActConstants.woodBulletID){
                AudioManager.instance.play(AssetsUI.instance.sounds.bullet_Wood_Hit);
            }else if(userData1.contactId==ActConstants.sandBulletID||userData2.contactId==ActConstants.sandBulletID){
                AudioManager.instance.play(AssetsUI.instance.sounds.bullet_Sand_Hit);
            }

            if(userData1.contactId==ActConstants.windBulletID||
                    userData1.contactId==ActConstants.iceBulletID||
                    userData1.contactId==ActConstants.sandBulletID||
                    userData1.contactId==ActConstants.woodBulletID||
                    userData1.contactId==ActConstants.laserID){

                    BulletSkill bulletSkill = (BulletSkill) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
                    bulletSkill.dispose();


            }else{

                    BulletSkill bulletSkill = (BulletSkill) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
                    bulletSkill.dispose();


            }
        }
    }
}