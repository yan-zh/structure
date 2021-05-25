package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.SkillGroupManager.SkillGourpFire;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.Fairy;

public class WindFairyAndMainCharacter implements ContactReaction {
    public static long contactID = 0b101;

    public WindFairyAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react() {
        Fairy fairy= ((Fairy)ActConstants.publicInformation.get("WindFairy"));
        if(fairy!=null){
            ActConstants.skillGroups[fairy.numberPosition] = new SkillGourpFire();
            fairy.removeBody();
            fairy.state=false;


            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    ((Fairy)ActConstants.publicInformation.get("WindFairy")).state=true;
                    ((Fairy)ActConstants.publicInformation.get("WindFairy")).remove();
                }
            });

            Action action = Actions.delay(0.5f,delayedAction);//这个数就是1s
            fairy.addAction(action);

        }



    }
}
