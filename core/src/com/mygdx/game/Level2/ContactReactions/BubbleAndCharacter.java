package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.Bubbles;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.PhysicalActions.DeletePhysicalEntity;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BubbleAndCharacter implements ContactReaction {
    public static long contactID= ActConstants.BubbleID+ActConstants.mainCharacterID;
    public BubbleAndCharacter(){
        ActConstants.contactList.put(contactID,this);
    }
    @Override
    public void react(UserData userData1, UserData userData2){
        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.MainCharacterState.replace("repulse",false);
        String bubbleIndexStr;
        if(userData1.contactId==ActConstants.BubbleID){
            bubbleIndexStr=userData1.nameInPublicInformation;
        }
        else {
            bubbleIndexStr=userData2.nameInPublicInformation;
        }

//        System.out.println("contact index:"+bubbleIndex);
        Bubbles bubbles = (Bubbles) ActConstants.publicInformation.get("bubbles");
        int bubbleIndex = Integer.parseInt(bubbleIndexStr)-bubbles.numOfDestroyed;
        System.out.println("array length:"+bubbles.bubbles.size+" bubbleIndex:"+bubbleIndex);
        Body body=bubbles.bubbles.get(bubbleIndex);
//        System.out.println(body);
        MainCharacter character = (MainCharacter) ActConstants.publicInformation.get("MainCharacter");
        Vector2 position= character.mySimulation.getPosition();
        character.mySimulation.setLinearVelocity(0,0);
        character.mySimulation.applyLinearImpulse(new Vector2(0,400),position,true);
        World world = bubbles.world;

//        bubbles.bubbles.removeIndex(index);
        deleteBody(body,world);

    }

    public void deleteBody(Body mySimulation, World world){
        DeletePhysicalEntity deletePhysicalEntity = new DeletePhysicalEntity();
        deletePhysicalEntity.deleteBody(mySimulation,world);
        ActConstants.physicalActionList.add(deletePhysicalEntity);
    }

}
