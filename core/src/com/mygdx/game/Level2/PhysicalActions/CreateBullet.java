package com.mygdx.game.Level2.PhysicalActions;

import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.ReflectiveStone;
import com.mygdx.game.Level2.NormalActors.laserTransmitter;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Tools.Assets;
import com.mygdx.game.Tools.MyVector;
import com.mygdx.game.abstraction.BulletSkill;
import com.mygdx.game.abstraction.PhysicalAction;

public class CreateBullet implements PhysicalAction {
    @Override
    public void act() {
        if(ActConstants.BulletCount==1) {
        ReflectiveStone reflectiveStone = ((ReflectiveStone)ActConstants.publicInformation.get("ReflectiveStone1"));
        reflectiveStone.emmit();
        System.out.println("The ReflectiveStone is triggered");}
        ActConstants.BulletCount = 2;
    }
}
