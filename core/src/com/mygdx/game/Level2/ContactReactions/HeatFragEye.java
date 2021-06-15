package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.Frag;
import com.mygdx.game.Level2.NormalActors.Frag2;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class HeatFragEye implements ContactReaction {

    public static long contactID1 = ActConstants.toadEyeID+ActConstants.windBulletID;
    public static long contactID2 = ActConstants.toadEyeID+ActConstants.woodBulletID;
    public static long contactID3 = ActConstants.toadEyeID+ActConstants.sandBulletID;
    public static long contactID4 = ActConstants.toadEyeID+ActConstants.iceBulletID;

    public HeatFragEye() {
        ActConstants.contactList.put(contactID1,this);
        ActConstants.contactList.put(contactID2,this);
        ActConstants.contactList.put(contactID3,this);
        ActConstants.contactList.put(contactID4,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
//        if(userData1.contactId==ActConstants.toadEyeID){
//            Frag frag = (Frag) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
//            frag.active();
//        }else{
//            Frag frag = (Frag) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
//            frag.active();
//        }


        if(userData1.contactId==ActConstants.toadEyeID){
            Frag2 frag2 = (Frag2) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            frag2.active();
        }else{
            Frag2 frag2 = (Frag2) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            frag2.active();
        }
    }
}
