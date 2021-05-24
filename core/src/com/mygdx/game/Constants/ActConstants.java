package com.mygdx.game.Constants;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.abstraction.ContactReaction;
import com.mygdx.game.abstraction.SkillGroup;

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
    }

    public static SkillGroup[] skillGroups;
    static{
        skillGroups = new SkillGroup[5];
    }

    public static int currentSkillGroup;




    public static HashMap<Long, ContactReaction> contactList;
    static
    {
        contactList = new HashMap<Long,ContactReaction>();
    }


    public static long mainCharacterID = 0x1;
    public static long ground = 0x10;
    //碰撞类型ID表
    //  0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0000 | 0000 0000 0000 0011



}
