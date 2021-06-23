package com.mygdx.game.Level3.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level3.NormalActors.SleepingBear;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BearAndMainCharacter implements ContactReaction {

    private static boolean hasMoved=false;
    private static void moved(){
        hasMoved=true;
    }
    public static long contactID= ActConstants.BearID+ActConstants.mainCharacterID;

    public BearAndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }
    @Override
    public void react(UserData userData1, UserData userData2) {
        //Refresh the state of jump
//        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.mainCharacter.reFreshJump();

        SleepingBear bear = (SleepingBear) ActConstants.publicInformation.get("sleepingBear");

        if(BearAndMainCharacter.hasMoved==false){
//            bear.body.setLinearVelocity(5,0);
MoveByAction action=Actions.moveBy(73f,0,2f);
bear.addAction(action);

            BearAndMainCharacter.moved();
        }

//        bear.body.setLinearVelocity(0,0);

    }
}

//public void move(){}