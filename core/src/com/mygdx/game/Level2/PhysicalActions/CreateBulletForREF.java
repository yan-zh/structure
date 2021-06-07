package com.mygdx.game.Level2.PhysicalActions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.ReflectiveStone;
import com.mygdx.game.abstraction.PhysicalAction;

public class CreateBulletForREF  implements PhysicalAction {
    @Override
    public void act() {
        if (ActConstants.BulletCount == 1) {
            ReflectiveStone reflectiveStone = ((ReflectiveStone) ActConstants.publicInformation.get("ReflectiveStone1"));
            reflectiveStone.emmit();
            System.out.println("The ReflectiveStone is triggered");
        }
        ActConstants.BulletCount = 2;
    }
}
