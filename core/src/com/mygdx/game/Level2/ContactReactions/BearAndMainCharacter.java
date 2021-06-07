package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.SleepingBear;
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
        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.MainCharacterState.replace("repulse",false);
        SleepingBear bear = (SleepingBear) ActConstants.publicInformation.get("sleepingBear");

        if(BearAndMainCharacter.hasMoved==false){
//            bear.body.setLinearVelocity(5,0);
MoveByAction action=Actions.moveBy(5,0,1f);
bear.addAction(action);

            BearAndMainCharacter.moved();
        }

//        bear.body.setLinearVelocity(0,0);

    }
}

//public void move(){}
