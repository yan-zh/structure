package com.mygdx.game.Level3.ContactReactions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level3.NormalActors.ReflectiveStone;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class ReflectiveStoneAndMain implements ContactReaction {

    public static long contactID = ActConstants.ReflectiveStoneID + ActConstants.mainCharacterID;

    String name;

    public ReflectiveStoneAndMain()
    {
        ActConstants.contactList.put(contactID, this);

    }

    @Override
    public void react(UserData userData1, UserData userData2) {

        if(userData1.contactId==ActConstants.ReflectiveStoneID){
            name=userData1.nameInPublicInformation;
            ReflectiveStone reflectiveStone= ((ReflectiveStone)ActConstants.publicInformation.get(name));

            reflectiveStone.removeBody();
            reflectiveStone.state=false;


            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    synchronized (ActConstants.publicInformationLock){
                        ((ReflectiveStone)ActConstants.publicInformation.get(name)).state=true;
                        ((ReflectiveStone)ActConstants.publicInformation.get(name)).remove();
                    }

                }
            });

            Action action = Actions.delay(0.5f,delayedAction);//这个数就是1s
            reflectiveStone.addAction(action);

        }else{
            name=userData2.nameInPublicInformation;
            ReflectiveStone reflectiveStone= ((ReflectiveStone)ActConstants.publicInformation.get(name));

            reflectiveStone.removeBody();
            reflectiveStone.state=false;


            Action delayedAction = Actions.run(new Runnable() {
                @Override
                public void run() {
                    synchronized (ActConstants.publicInformationLock){
                        ((ReflectiveStone)ActConstants.publicInformation.get(name)).state=true;
                        ((ReflectiveStone)ActConstants.publicInformation.get(name)).remove();
                    }

                }
            });

            Action action = Actions.delay(0.5f,delayedAction);//这个数就是1s
            reflectiveStone.addAction(action);
        }

    }
}
