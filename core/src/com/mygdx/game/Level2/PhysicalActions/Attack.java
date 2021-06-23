package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.MainCharacter;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.asset.AssetsLevel2;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.PhysicalAction;

public class Attack implements PhysicalAction {



    float mouseX;
    float mouseY;
    int type;

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
            currentStage.addActor(new BulletSkill(AssetsUI.instance.spriteAttack.fzd,AssetsUI.instance.spriteAttack.animFhit,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.windBulletID,3,1));
        }else if(type==2){
            currentStage.addActor(new BulletSkill(AssetsUI.instance.spriteAttack.mzd,AssetsUI.instance.spriteAttack.animMhit,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.sandBulletID,3,2));
        }else if(type==3){
            currentStage.addActor(new BulletSkill(AssetsUI.instance.spriteAttack.tzd,AssetsUI.instance.spriteAttack.animThit,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.woodBulletID,3,3));
        }else if(type==4){
            currentStage.addActor(new BulletSkill(AssetsLevel2.instance.decoration.jiguang,AssetsUI.instance.spriteAttack.animFhit,(int)mainCharacter.getX(),(int)mainCharacter.getY(),direction,ActConstants.laserID,3,4));
        }

    }
}
