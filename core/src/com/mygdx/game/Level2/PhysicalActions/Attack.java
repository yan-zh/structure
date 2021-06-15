package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.PhysicalAction;

public class Attack implements PhysicalAction {



    float mouseX;
    float mouseY;
    int type;//1 风 2 土 3 木

    public Attack(float mouseX, float mouseY, int type) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.type = type;
    }

    @Override
    public void act() {
        Stage currentStage = (Stage) ActConstants.publicInformation.get("CurrentStage");
        MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get("MainCharacter");
        float[] direction;
        direction = MyVector.getStandardVector(mainCharacter.getX(),mainCharacter.getY(),mouseX,mouseY);
        if(type==1){
            currentStage.addActor(new BulletSkill(Assets.instance.bunny.head,Assets.instance.bunny.getAnimCopterRotate,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.windBulletID,10));
        }else if(type==2){
            currentStage.addActor(new BulletSkill(Assets.instance.bunny.head,Assets.instance.bunny.getAnimCopterRotate,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.sandBulletID,10));
        }else if(type==3){
            currentStage.addActor(new BulletSkill(Assets.instance.bunny.head,Assets.instance.bunny.getAnimCopterRotate,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.woodBulletID,10));
        }else if(type==4){
            currentStage.addActor(new BulletSkill(Assets.instance.bunny.head,Assets.instance.bunny.getAnimCopterRotate,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.laserID,10));
        }

    }
}
