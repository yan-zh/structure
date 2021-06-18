package com.mygdx.game.Level1.PhysicalActions;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.PhysicalAction;

public class KeyBulletCreate implements PhysicalAction {

    float mouseX;
    float mouseY;

    public KeyBulletCreate(float mouseX, float mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    public void act() {

        Stage currentStage = (Stage) ActConstants.publicInformation.get("CurrentStage");
        MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get("MainCharacter");
        float[] direction;
        direction = MyVector.getStandardVector(mainCharacter.getX(),mainCharacter.getY(),mouseX,mouseY);
        currentStage.addActor(new BulletSkill(Assets.instance.bunny.head,Assets.instance.bunny.getAnimCopterRotate,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.windBulletID,1,2));

    }
}
