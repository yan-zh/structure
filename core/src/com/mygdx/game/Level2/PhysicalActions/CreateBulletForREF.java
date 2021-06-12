package com.mygdx.game.Level2.PhysicalActions;

import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.ReflectiveStone;
import com.mygdx.game.Level2.NormalActors.StillReflectiveStone;
import com.mygdx.game.abstraction.PhysicalAction;

public class CreateBulletForREF  implements PhysicalAction {

    String name;

    public CreateBulletForREF(String name) {
        this.name = name;
    }

    @Override
    public void act() {
        if (ActConstants.BulletCount == 1) {
            if(name.contains("still")){
                StillReflectiveStone reflectiveStone = ((StillReflectiveStone) ActConstants.publicInformation.get(name));
                System.out.println("This is the name:"+name);
                reflectiveStone.emmit();
                System.out.println("The ReflectiveStone is triggered");
            }else{
                ReflectiveStone reflectiveStone = ((ReflectiveStone) ActConstants.publicInformation.get(name));
                System.out.println("This is the name:"+name);
                reflectiveStone.emmit();
                System.out.println("The ReflectiveStone is triggered");
            }

        }
        ActConstants.BulletCount = 2;
    }
}
