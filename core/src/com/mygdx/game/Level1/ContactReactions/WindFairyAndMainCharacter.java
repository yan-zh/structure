package com.mygdx.game.Level1.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.SkillGroupManager.SkillGroupWind;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.Fairy;
import com.mygdx.game.abstraction.UserData;

public class WindFairyAndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.windFairyID+ActConstants.mainCharacterID;

    public WindFairyAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1,UserData userData2) {
        Fairy fairy= ((Fairy)ActConstants.publicInformation.get("WindFairy"));
        if(fairy!=null){
            ActConstants.skillGroups[1] = new SkillGroupWind();
            fairy.removeBody();
            fairy.state=false;
            AssetsUI.instance.addSprit();


            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    synchronized (ActConstants.publicInformationLock){
                        ((Fairy)ActConstants.publicInformation.get("WindFairy")).state=true;
                        ((Fairy)ActConstants.publicInformation.get("WindFairy")).remove();
                    }

                }
            });

            Action action = Actions.delay(0.5f,delayedAction);
            fairy.addAction(action);
        }



    }
}