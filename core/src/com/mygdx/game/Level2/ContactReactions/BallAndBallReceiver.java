package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.BallLauncher;
import com.mygdx.game.Level2.NormalActors.BallReceiver;
import com.mygdx.game.Level2.NormalActors.Portal;
import com.mygdx.game.Level2.NormalActors.ReboundBall;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsLevel1;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BallAndBallReceiver implements ContactReaction {


    int number;

    public static long contactID = ActConstants.ballReceiverID+ActConstants.reboundBallID;

    public BallAndBallReceiver() {
        number=0;
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        Portal portal = (Portal) ActConstants.publicInformation.get("PortalIceTriggered");
        if(portal!=null) {
            portal.turnOn();
        }

        AudioManager.instance.play(AssetsLevel1.instance.sounds.follower_Refuse);

        if(number==0){
            if(userData1.contactId==ActConstants.reboundBallID){
                ReboundBall reboundBall = (ReboundBall) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
                reboundBall.remove();
                BallReceiver ballReceiver = (BallReceiver) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
                ballReceiver.hit = true;
                ballReceiver.fire = true;
            }else{
                ReboundBall reboundBall = (ReboundBall) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
                reboundBall.remove();
                BallReceiver ballReceiver = (BallReceiver) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
                ballReceiver.hit = true;
                ballReceiver.fire = true;
            }
        }

        number = 1;
    }
}
