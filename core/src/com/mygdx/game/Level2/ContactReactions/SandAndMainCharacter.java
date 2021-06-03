package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.NormalActors.SandPlat;
import com.mygdx.game.Level2.NormalActors.brokenBridge;
import com.mygdx.game.Level2.PhysicalActions.CreateBullet;
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
        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.MainCharacterState.replace("repulse",false);
        if(sandPlat != null && mainCharacter.mySimulation.getLinearVelocity().y<0)
        {



            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation)).state = true;
                    ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation)).removeBody();
                    ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation)).state = false;


                    Timer timer = new Timer();
                    Timer.Task timerTask = new Timer.Task() {
                        @Override

                        public void run() {
                            SandPlat sandPlat = ((SandPlat)ActConstants.publicInformation.get(userData.nameInPublicInformation));
                            sandPlat.createBody();

                        }

                    };
                    timer.scheduleTask(timerTask, 1, 1, 1);// 0s之后执行，每次间隔1s，执行20次。

                }
            });
            Action action = Actions.delay(0.5f, delayedAction);
            sandPlat.addAction(action);
        }
    }
}
