package com.mygdx.game.Constants;

import com.badlogic.gdx.InputMultiplexer;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.PhysicalAction;
import com.mygdx.game.abstraction.SkillGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class ActConstants {
    //游戏界面大小
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;

    public static float worldSize_pAndPhysic = 50;
    public static float worldSize_shapeAndPhysics = 2f;

    public static InputMultiplexer inputMultiplexer;


    public static MainCharacter mainCharacter;

    public static float MainCharacterUpImpulse = 600f;
    public static float MainCharacterSpeed = 5;

    public static int CameraCharacterControl=5;

    public static HashMap<String,Boolean> MainCharacterState;
    static
    {
        MainCharacterState = new HashMap<String, Boolean>();
        MainCharacterState.put("goLeft",false);
        MainCharacterState.put("goRight",false);
        MainCharacterState.put("repulse",false);
//        MainCharacterState.put("slowDown",false);
        MainCharacterState.put("onGround",true);
//        MainCharacterState.put("noControl",true);
//        MainCharacterState.put("froze",false);
//        MainCharacterState.put("jumping",false);
    }

//这两个是物理实体块的类型
    //public static String ground = "ground";

    //public static MainCharacter mainCharacter;

    public static HashMap<String,Object> publicInformation;
    static
    {
        publicInformation = new HashMap<>();
        publicInformation.put("CurrentStage",null);
    }

    public static SkillGroup[] skillGroups;
    static{
        skillGroups = new SkillGroup[5];

    }

    public static int currentSkillGroup;
    static{
        currentSkillGroup=1;
    }




    public static HashMap<Long, ContactReaction> contactList;
    static
    {
        contactList = new HashMap<>();
    }


    public static long mainCharacterID = 0b1;
    public static long groundID = 0b10;
    public static long windFairyID = 0b100;
    //碰撞类型ID表
    //  0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0010
   //   0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0011




    //阎之泓**********************

    public static long windBulletID = 0b10000;

    public static long woodBulletID = 0b10000000000;

    public static long sandBulletID = 0b10000000000;

    public static long iceBulletID = 0b100000000000;

    public static long spineID = 0b1000000;

    public static long Boss1ID = 0b100000000;

    public static long Boss1SensorID = 0b10000000000000;

    public static long axeID = 0b100000000000000;

    public static long axeSensorID = 0b1000000000000000;

    public static long toadEyeID = 0b100000000000000000;

    public static long MonsterID = 0b10000000000000000000;

    public static long monsterSensorID = 0b1000000000000000000000;

    public static long flowerID = 0b100000000000000000000000000;

    public static long iceFairyID = 0b1000000000000000000000000000;

    public static long sandFairyID = 0b10000000000000000000000000000;

    public static long woodFairyID = 0b100000000000000000000000000000;

    public static long eatPlatformID = 0b10000000000000000000000000000000;

    public static long tongueMonsterID;
    static{
        tongueMonsterID=1;
        tongueMonsterID = tongueMonsterID<<33;
    }


    public static ArrayList<PhysicalAction> physicalActionList;
    static{
        physicalActionList = new ArrayList<>();
    }


    public static Integer monsterLock;
    static{
        monsterLock=1;
    }


    public static Integer axeLock;
    static{
        axeLock=2;
    }

    public static int countnumber;
    static{
        countnumber=0;
    }

    public static Integer bulletLock;
    static{
        bulletLock=3;
    }

    public static Integer MonsterActionLock;
    static{
        MonsterActionLock =4;
    }

    public static boolean isFrozen;
    static{
        isFrozen = false;
    }

    //阎之泓part________________________

    //郐洋Part
    public static long brokenBridgeID = 0b1000;
    //0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 1000

    public static long portalID = 0b10000000;
    //0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 1000 0000

    public static long switchID = 0b1000000000000000000;

    public static long IceID = 0b100000000000000000000;

    //****************郐洋


    //***************林韫奇
    public static long platformID = 0b100000;
    public static long thinSurfaceID= 0b1000000000000000000000000000000;
    public static long hangedBallsID=0b10000000000000000;

}
