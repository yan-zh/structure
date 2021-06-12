package com.mygdx.game.SkillGroupManager;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.PhysicalActions.Attack;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.abstraction.SkillGroup;

public class SkillGroupSand implements SkillGroup {

    public void skill1(float mouseX, float mouseY){
        Stage currentStage = (Stage) ActConstants.publicInformation.get("CurrentStage");
        MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get("MainCharacter");
        float[] direction;
        direction = MyVector.getStandardVector(mainCharacter.getX(),mainCharacter.getY(),mouseX,mouseY);
        currentStage.addActor(new fireBoll(mainCharacter.getX(),mainCharacter.getY(),direction));

    }

    public void skill2(float mouseX, float mouseY){

        Attack attack = new Attack(mouseX,mouseY,2);
        ActConstants.physicalActionList.add(attack);

    }
}
