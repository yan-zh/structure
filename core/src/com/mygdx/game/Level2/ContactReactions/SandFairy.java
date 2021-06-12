package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.SkillGroupManager.SkillGroupWind;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.Fairy;
import com.mygdx.game.abstraction.UserData;

public class SandFairy implements ContactReaction {


    public static long contactID = ActConstants.sandFairyID+ActConstants.mainCharacterID;

    public SandFairy() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1,UserData userData2) {
        Fairy fairy = ((Fairy) ActConstants.publicInformation.get("SandFairy"));
        if (fairy != null) {
            ActConstants.skillGroups[fairy.numberPosition] = new SkillGroupWind();
            fairy.removeBody();
            fairy.state = false;


            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    synchronized (ActConstants.publicInformationLock) {
                        ((Fairy) ActConstants.publicInformation.get("SandFairy")).state = true;
                        ((Fairy) ActConstants.publicInformation.get("SandFairy")).remove();
                    }

                }
            });

            Action action = Actions.delay(0.5f, delayedAction);//这个数就是1s
            fairy.addAction(action);
        }

    }
}
