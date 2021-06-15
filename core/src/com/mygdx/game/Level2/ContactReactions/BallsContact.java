package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.HangedBalls;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BallsContact implements ContactReaction {
    //  ballId: 0b10000000000000000
    public static long contactID1 = 0b10000000000+0b10000000000000000;
    public static long contactID2 = 0b100000000000+0b10000000000000000;
    public static long contactID3 = 0b1000000000000+0b10000000000000000;
    public static long contactID4 = 0b10000+0b10000000000000000;
        public static long contactID = 0b1+0b10000000000000000;


    public BallsContact(){
        ActConstants.contactList.put(contactID1, this);
        ActConstants.contactList.put(contactID2,this);
        ActConstants.contactList.put(contactID3,this);
        ActConstants.contactList.put(contactID4,this);
                ActConstants.contactList.put(contactID,this);


    }
    @Override
    public void  react(UserData userData1, UserData userData2){
        String i;
        if(userData1.contactId==0b10000000000000000){
            i=userData1.nameInPublicInformation.substring(userData1.nameInPublicInformation.length() - 1);
        }
        else{
            i=userData2.nameInPublicInformation.substring(userData2.nameInPublicInformation.length() - 1);
        }
      HangedBalls balls= (HangedBalls) ActConstants.publicInformation.get("hangedBalls");
        balls.hit(i);
       System.out.println("object "+balls);
        System.out.println(i);
        AudioManager.instance.play(AssetsLevel0.instance.sounds.ball_Triggered);



    }

}
