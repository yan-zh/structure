package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.NormalActors.Portal;
import com.mygdx.game.Level2.NormalActors.ReflectiveStone;
import com.mygdx.game.Level2.NormalActors.laserTransmitter;
import com.mygdx.game.Level2.PhysicalActions.CreateBullet;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class ReflectiveStoneAndBullet implements ContactReaction {
    public static long contactID = ActConstants.ReflectiveStoneID + ActConstants.iceBulletID;
    public ReflectiveStoneAndBullet()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        Timer timer = new Timer();
        Timer.Task timerTask = new Timer.Task() {
            @Override

            public void run() {
                CreateBullet createBullet = new CreateBullet();
                ActConstants.BulletCount = 1;
                createBullet.act();
                }

        };
        timer.scheduleTask(timerTask, 1, 1, 1);// 0s之后执行，每次间隔1s，执行20次。



    }
}
