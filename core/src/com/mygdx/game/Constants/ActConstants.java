package com.mygdx.game.Constants;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.Body;
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
        MainCharacterState.put("slowDown",false);
        MainCharacterState.put("onGround",true);
        MainCharacterState.put("noControl",true);
        MainCharacterState.put("froze",false);
        MainCharacterState.put("jumping",false);
    }

//这两个是物理实体块的类型
    //public static String ground = "ground";

    //public static MainCharacter mainCharacter;

    public static HashMap<String,Object> publicInformation;
    static
    {
        publicInformation = new HashMap<String,Object>();
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
        contactList = new HashMap<Long,ContactReaction>();
    }


    public static long mainCharacterID = 0b1;
    public static long groundID = 0b10;
    public static long windFairyID = 0b100;
    //碰撞类型ID表
    //  0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0010
   //   0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0011




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

    public static ArrayList<PhysicalAction> physicalActionList;
    static{
        physicalActionList = new ArrayList<>();
    }


    //yzh_________________________



}
