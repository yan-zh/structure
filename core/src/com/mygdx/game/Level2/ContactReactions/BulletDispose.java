package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BulletDispose implements ContactReaction {
    public static long contactID1 = ActConstants.windBulletID;
    public static long contactID2 = ActConstants.woodBulletID;
    public static long contactID3 = ActConstants.sandBulletID;
    public static long contactID4 = ActConstants.iceBulletID;
    public static long contactID5 = ActConstants.ReflectiveStoneID;


    public BulletDispose() {
        ActConstants.contactList.put(contactID1,this);
        ActConstants.contactList.put(contactID2,this);
        ActConstants.contactList.put(contactID3,this);
        ActConstants.contactList.put(contactID4,this);
        ActConstants.contactList.put(contactID5,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
//
//        ActConstants.countnumber++;
//        System.out.println(ActConstants.countnumber);
        if(userData1.contactId!=ActConstants.monsterSensorID&&userData2.contactId!=ActConstants.monsterSensorID){
            if(userData1.contactId==ActConstants.windBulletID||
                    userData1.contactId==ActConstants.iceBulletID||
                    userData1.contactId==ActConstants.sandBulletID||
                    userData1.contactId==ActConstants.ReflectiveStoneID||
                    userData1.contactId==ActConstants.woodBulletID){
                BulletSkill bulletSkill = (BulletSkill) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
                bulletSkill.contactMark=true;
                bulletSkill.flyMark=false;
                bulletSkill.deleteBody();

            }else{
                BulletSkill bulletSkill = (BulletSkill) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
                bulletSkill.contactMark=true;
                bulletSkill.flyMark=false;
                bulletSkill.deleteBody();
            }
        }



    }
}
