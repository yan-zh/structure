package com.mygdx.game.Level2.ContactReactions;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level3.NormalActors.Bubbles;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.PhysicalActions.DeleteBubble;
import com.mygdx.game.Tools.AudioManager;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.UserData;

public class BubbleAndCharacter implements ContactReaction {
    public static long contactID= ActConstants.BubbleID+ActConstants.mainCharacterID;
    public BubbleAndCharacter(){
        ActConstants.contactList.put(contactID,this);
    }
    @Override
    public void react(UserData userData1, UserData userData2){
//        ActConstants.MainCharacterState.replace("onGround",true);
        ActConstants.mainCharacter.reFreshJump();
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
//        System.out.println(bubbles.getY());
        float posX=body.getPosition().x;
        float posY=bubbles.getY();
        deleteBody(body,posX,posY);
        AudioManager.instance.play(AssetsLevel2.instance.sounds.bubbleBound);
//        body.setTransform(posX,posY,0);
//        bubbles.bubbles.removeIndex(index);
//        deleteBody(body,world);

    }

    public void deleteBody(Body mySimulation, float posX, float posY){
        DeleteBubble deleteBubble = new DeleteBubble();
        deleteBubble.deleteBody(mySimulation,posX,posY);
        ActConstants.physicalActionList.add(deleteBubble);
    }

}
