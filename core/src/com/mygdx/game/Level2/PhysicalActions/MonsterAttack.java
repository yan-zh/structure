package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.Tools.asset.AssetsUI;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.PhysicalAction;

public class MonsterAttack implements PhysicalAction {

    Body mySimulation;
    float thisX;
    float thisY;
    int type;

    public MonsterAttack(Body mySimulation, float thisX, float thisY, int type) {
        this.mySimulation = mySimulation;
        this.thisX = thisX;
        this.thisY = thisY;
        this.type = type;
    }

    @Override
    public void act() {
        MainCharacter mainCharacter = (MainCharacter) ActConstants.publicInformation.get("MainCharacter");
        float[] direction = MyVector.getStandardVector(mySimulation.getPosition().x,mySimulation.getPosition().y,mainCharacter.getPhysicalX(),mainCharacter.getPhysicalY());
        if(type==1){
            MyGdxGame.currentStage.addActor(new BulletSkill(AssetsUI.instance.spriteAttack.fzd,AssetsUI.instance.spriteAttack.animFhit,thisX,thisY,direction,ActConstants.windBulletID,1,1));
        }else if(type==2){
            MyGdxGame.currentStage.addActor(new BulletSkill(AssetsUI.instance.spriteAttack.fzd,AssetsUI.instance.spriteAttack.animFhit,thisX,thisY,direction,ActConstants.windBulletID,1,2));
        }else if(type==3){
            MyGdxGame.currentStage.addActor(new BulletSkill(AssetsUI.instance.spriteAttack.fzd,AssetsUI.instance.spriteAttack.animFhit,thisX,thisY,direction,ActConstants.windBulletID,1,3));
        }

    }
}
