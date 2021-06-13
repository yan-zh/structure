package com.mygdx.game.Level2.ContactReactions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.NormalActors.Boss1;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.MyStage;
import com.mygdx.game.abstraction.UserData;

public class Boss1AndMainCharacter implements ContactReaction {

    public static long contactID = ActConstants.Boss1ID+ActConstants.mainCharacterID;

    public Boss1AndMainCharacter() {
        ActConstants.contactList.put(contactID,this);
    }

    @Override
    public void react(UserData userData1, UserData userData2) {
        if(userData1.contactId==ActConstants.mainCharacterID){
            MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
            mainCharacter.die();
//            Boss1 boss1 = (Boss1) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
//            boss1.remove();
//            MyGdxGame.currentStage.addActor(new Boss1(((MyStage)MyGdxGame.currentStage).getWorld(),496,12));
        }else{
            MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get(userData2.nameInPublicInformation);
            mainCharacter.die();
//            Boss1 boss1 = (Boss1) ActConstants.publicInformation.get(userData1.nameInPublicInformation);
//            boss1.remove();
//            MyGdxGame.currentStage.addActor(new Boss1(((MyStage)MyGdxGame.currentStage).getWorld(),496,12));
        }
    }
}
