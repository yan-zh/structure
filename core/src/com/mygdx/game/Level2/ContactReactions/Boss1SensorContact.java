package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.NormalActors.Boss1;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsLevel0;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class Boss1SensorContact implements ContactReaction {
    public static long contactID = ActConstants.Boss1SensorID+ActConstants.mainCharacterID;

    public Boss1SensorContact() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        ((Boss1)ActConstants.publicInformation.get("Boss1")).active();
        AudioManager.instance.stopMusic();
        AudioManager.instance.play(AssetsLevel0.instance.music.startBoss);

    }
}
