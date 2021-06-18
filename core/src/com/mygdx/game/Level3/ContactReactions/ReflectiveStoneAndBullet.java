package com.mygdx.game.Level3.ContactReactions;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.PhysicalActions.CreateBulletForREF;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class ReflectiveStoneAndBullet implements ContactReaction {

    public static long contactID = ActConstants.ReflectiveStoneID + ActConstants.laserID;
    public static long contactID2 = ActConstants.stillReflectiveStoneID + ActConstants.laserID;
    String name;

    public ReflectiveStoneAndBullet()
    {
        ActConstants.contactList.put(contactID, this);
        ActConstants.contactList.put(contactID2, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {


        if(userData1.contactId==ActConstants.ReflectiveStoneID||userData1.contactId==ActConstants.stillReflectiveStoneID){
            name = userData1.nameInPublicInformation;
        }else{
            name = userData2.nameInPublicInformation;
        }

        Timer timer = new Timer();
        Timer.Task timerTask = new Timer.Task() {
            @Override

            public void run() {
                CreateBulletForREF createBullet = new CreateBulletForREF(name);
                ActConstants.BulletCount = 1;
                createBullet.act();
                }

        };
        timer.scheduleTask(timerTask, 1, 1, 0);// 0s之后执行，每次间隔1s，执行20次。



    }
}
