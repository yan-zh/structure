package com.mygdx.game.Level3.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.MainCharacter;
import com.mygdx.game.Level3.NormalActors.SandPlat;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class SandAndMainCharacter implements ContactReaction {
    public static long contactID = ActConstants.SandID + ActConstants.mainCharacterID;
    private int stateJudge = 0;
    public SandAndMainCharacter()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        final UserData userData;
        if(userData1.contactId == ActConstants.SandID){
            userData = userData1;
        }
        else userData = userData2;

        System.out.println("The sand is triggered");
        SandPlat sandPlat = ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation));
        MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));

        //Refresh the state of jump
        ActConstants.mainCharacter.reFreshJump();
        if(sandPlat != null && mainCharacter.mySimulation.getLinearVelocity().y<0)
        {



            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation)).state = false;
                    ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation)).removeBody();


                    Timer timer = new Timer();
                    Timer.Task timerTask = new Timer.Task() {
                        @Override

                        public void run() {
                            SandPlat sandPlat = ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation));
                            ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation)).state = false;

                            sandPlat.createBody();

                        }

                    };
                    timer.scheduleTask(timerTask, 2, 1, 1);
                }
            });
            Action action = Actions.delay(1f, delayedAction);
            sandPlat.addAction(action);
        }
    }
}
