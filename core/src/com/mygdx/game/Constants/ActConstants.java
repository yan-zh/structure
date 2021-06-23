package com.mygdx.game.Constants;

import com.badlogic.gdx.InputMultiplexer;
import com.mygdx.game.abstraction.MainCharacter;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.PhysicalAction;
import com.mygdx.game.abstraction.SkillGroup;

import java.util.ArrayList;
import java.util.HashMap;

public class ActConstants {

    public static String currentPortal;

    public static float changeStageTo=0;

    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;

    public static float worldSize_pAndPhysic = 50;
    public static float worldSize_shapeAndPhysics = 2f;

    public static InputMultiplexer inputMultiplexer;


    public static MainCharacter mainCharacter;

    public static float MainCharacterUpImpulse = 450f;
    public static float MainCharacterSpeed = 7;

    public static int CameraCharacterControl=5;
    public static int spriteNumber = 1 ;
    public static int mainUserLive = 20 ;
    public static int mainUserLiveMax = 20 ;

    public static HashMap<String,Boolean> MainCharacterState;
    static
    {
        MainCharacterState = new HashMap<String, Boolean>();
        MainCharacterState.put("goLeft",false);
        MainCharacterState.put("goRight",false);
        MainCharacterState.put("repulse",false);
        MainCharacterState.put("onGround",true);
        MainCharacterState.put("blow",false);
    }


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

    public static int health = 20;




    public static HashMap<Long, ContactReaction> contactList;
    static
    {
        contactList = new HashMap<>();
    }


    public static long mainCharacterID = 0b1;
    public static long groundID = 0b10;
    public static long windFairyID = 0b100;



    //yzh**********************

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

    public static long reboundBallID;
    static{
        reboundBallID = 1;
        reboundBallID = reboundBallID<<38;
    }

    public static long blowerID;
    static{
        blowerID=1;
        blowerID = blowerID<<35;
    }

    public static long ballLauncherID;
    static{
        ballLauncherID = 1;
        ballLauncherID = ballLauncherID <<39;
    }

    public static long tongueMonsterID;
    static{
        tongueMonsterID=1;
        tongueMonsterID = tongueMonsterID<<33;
    }
    public static long BearID;
    static{
        BearID=1;
        BearID=BearID<<36;
    }

    public static long BubbleID;
    static{
        BubbleID=1;
        BubbleID=BubbleID<<43;
    }

    public static long mainCharacterSensorID;
    static{
        mainCharacterSensorID=1;
        mainCharacterSensorID = mainCharacterSensorID<<34;
    }

    public static long ballReceiverID;
    static{
        ballReceiverID = 1;
        ballReceiverID = ballReceiverID <<40;
    }

    public static long bossLauncherID;
    static{
        bossLauncherID = 1;
        bossLauncherID = bossLauncherID<<42;
    }

    public static long laserID;
    static{
        laserID = 1;
        laserID = laserID<<47;
    }

    public static long stillReflectiveStoneID;
    static{
        stillReflectiveStoneID = 1;
        stillReflectiveStoneID = stillReflectiveStoneID<<48;
    }

    public static long moveLauncherID;
    static{
        moveLauncherID = 1;
        moveLauncherID = moveLauncherID << 50;
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

    public static Integer bossLauncherLock;
    static{
        bossLauncherLock=5;
    }


    public final static Integer publicInformationLock;
    static{
        publicInformationLock=7;
    }

    public final static Integer physicalActionListLock;
    static{
        physicalActionListLock=7;
    }


    public final static Integer bulletDeleteLock;
    static{
        bulletDeleteLock = 9;
    }

    public final static Integer mainCharacterLock;
    static{
        mainCharacterLock = 10;
    }

    public static boolean isFrozen;
    static{
        isFrozen = false;
    }
    public static boolean canGravityInverse = true;


    public static long brokenBridgeID = 0b1000;
    //0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 1000

    public static long portalID = 0b10000000;
    //0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 1000 0000

    public static long switchID = 0b1000000000000000000;

    public static long IceID = 0b100000000000000000000;

    public static long SensorID;

    static{
        SensorID=1;
        SensorID = SensorID<<32;
    }
    public static long laserTransmitterID;

    static{
        laserTransmitterID=1;
        laserTransmitterID = laserTransmitterID<<34;
    }

    public static int SensorCount = 1;

    public static int BulletCount = 1;

    public static long ReflectiveStoneID;

    static{
        ReflectiveStoneID=1;
        ReflectiveStoneID = ReflectiveStoneID<<37;
    }


    public static long SandID;

    static{
        SandID=1;
        SandID = SandID<<41;
    }

    public static int SandCount = 1;


    public static boolean fragState = false;


    public static long beaconID;
    static{
        beaconID = 1;
        beaconID = beaconID << 49;
    }
    //****************kuaiy


    //***************lyq
    public static long platformID = 0b100000;
    public static long thinSurfaceID= 0b1000000000000000000000000000000;
    public static long hangedBallsID=0b10000000000000000;
    public static long PswDoorID;
    static{
        PswDoorID = 1;
        PswDoorID = PswDoorID<<44;
    }

    public static long BossID;
    static{
        BossID = 1;
        BossID = BossID << 45;
    }

    public static long LaserDoorID;
    static{
        LaserDoorID = 1;
        LaserDoorID = LaserDoorID << 51;
    }

}
