package com.mygdx.game.Listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level0.Stage0;
import com.mygdx.game.Level1.Stage1;
import com.mygdx.game.Level2.NormalActors.*;
import com.mygdx.game.Level2.PhysicalActions.CreateStone;
import com.mygdx.game.Level2.PhysicalActions.ReverseMainCharacterGravity;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.abstraction.MyStage;

public class UserInputListener extends InputListener {

    public UserInputListener() {
    }

    public boolean keyDown(InputEvent event, int keycode) {
        if (!ActConstants.MainCharacterState.get("repulse")) {
            if (event.getKeyCode() == Input.Keys.D) {
                ActConstants.MainCharacterState.replace("goRight", true);
                ActConstants.MainCharacterState.replace("goLeft", false);
                //ActConstants.MainCharacterState.replace("noControl",false);
            }
            if (event.getKeyCode() == Input.Keys.A) {
                ActConstants.MainCharacterState.replace("goRight", false);
                ActConstants.MainCharacterState.replace("goLeft", true);
                //ActConstants.MainCharacterState.replace("noControl",false);
            }
            if (event.getKeyCode() == Input.Keys.SPACE && ActConstants.MainCharacterState.get("onGround")) {

                ((MainCharacter) ActConstants.publicInformation.get("MainCharacter")).jump();
                //ActConstants.MainCharacterState.replace("onGround",false);

            }
            if (event.getKeyCode() == Input.Keys.Y) {
                MainCharacter mainCharacter = ((MainCharacter) ActConstants.publicInformation.get("MainCharacter"));
                System.out.println(mainCharacter.mySimulation.getPosition().x + "," + mainCharacter.mySimulation.getPosition().y);
            }
            if (event.getKeyCode() == Input.Keys.R) {
                MainCharacter mainCharacter = ((MainCharacter) ActConstants.publicInformation.get("MainCharacter"));
                System.out.println(mainCharacter.mySimulation.getPosition().x + "," + mainCharacter.mySimulation.getPosition().y);
            }

            //Portal Listener
            if (event.getKeyCode() == Input.Keys.K) {

                Portal portal = (Portal) ActConstants.publicInformation.get(ActConstants.currentPortal);
                if (portal != null) {
                    MainCharacter mainCharacter = ((MainCharacter) ActConstants.publicInformation.get("MainCharacter"));
                    if (portal.triggerState == true && portal.isActive == true) {

                        portal.state = false;

                        Action delayedAction = Actions.run(new Runnable() {
                            @Override
                            public void run() {
//                            ((Portal)ActConstants.publicInformation.get("Portal")).state = true;
                                Portal portal = (Portal) ActConstants.publicInformation.get(ActConstants.currentPortal);
                                if(portal.Stagetranto == "none") {
                                    MainCharacter mainCharacter = ((MainCharacter) ActConstants.publicInformation.get("MainCharacter"));
                                    mainCharacter.mySimulation.setTransform(new Vector2(portal.dstXPos, portal.dstYPos), 0);
                                }
                                Action delayedAction = Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
//                            ((Portal)ActConstants.publicInformation.get("Portal")).state = true;
                                        Portal portal = (Portal) ActConstants.publicInformation.get(ActConstants.currentPortal);

                                        portal.state = true;

                                        if (portal.Stagetranto == "Stage2") {
//                                          ActConstants.isChange = true;
//                                            ActConstants.goStage2 = true;
                                            ActConstants.changeStageTo=2;
                                        }
                                        if (portal.Stagetranto == "Stage3") {
//                                          ActConstants.isChange = true;
//                                            ActConstants.goStage2 = true;
                                            ActConstants.changeStageTo=3;
                                        }

                                        if (portal.Stagetranto == "PortalFinal") {
                                            ((MoveLauncher)ActConstants.publicInformation.get("moveLauncher")).start();
                                            ((MoveLauncher)ActConstants.publicInformation.get("moveLauncher")).startMove();
                                        }


                                    }
                                });
                                //一秒后回到之前的状态
                                Action action = Actions.delay(1f, delayedAction);
                                portal.addAction(action);


                            }
                        });
                        Action action = Actions.delay(3f, delayedAction);
                        portal.addAction(action);
                        Beacon beacon = (Beacon) ActConstants.publicInformation.get("Beacon");
                        beacon.setBeacon();

                    }
                }

            }
            //rotateSwitch
            if (event.getKeyCode() == Input.Keys.E) {
                rotateSwitch rotateSwitchDoor = ((rotateSwitch) ActConstants.publicInformation.get("rotateSwitchDoor"));
                rotateSwitch rotateSwitchFrag = ((rotateSwitch) ActConstants.publicInformation.get("rotateSwitchFrag"));
                rotateSwitch rotateSwitchPortal = ((rotateSwitch) ActConstants.publicInformation.get("rotateSwitchPortal"));
                if (rotateSwitchPortal != null) {
                    if (rotateSwitchPortal.triggerState == true) {
                        Portal portal = (Portal) ActConstants.publicInformation.get(ActConstants.currentPortal);
                        if (portal != null && rotateSwitchPortal.ControlType == "portal") {
                            rotateSwitchPortal.state = false;
                            portal.turnOn();
                            Action delayedAction = Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    rotateSwitch rotateSwitch = ((rotateSwitch) ActConstants.publicInformation.get("rotateSwitchPortal"));
                                    rotateSwitch.state = true;
                                }
                            });
                            Action action = Actions.delay(3f, delayedAction);
                            portal.addAction(action);
                        }
                    }
                }

                if (rotateSwitchDoor != null) {
                    if (rotateSwitchDoor.triggerState == true) {
                        Door door = ((Door) ActConstants.publicInformation.get("door"));
                        if (door != null) {
                            rotateSwitchDoor.state = false;
                            door.turnOn();
                            door.state = false;
                            Action delayedAction = Actions.run(new Runnable() {
                                @Override
                                public void run() {
                                    Door door = ((Door) ActConstants.publicInformation.get("door"));
                                    door.state = true;
                                    rotateSwitch rotateSwitch = ((rotateSwitch) ActConstants.publicInformation.get("rotateSwitchDoor"));
                                    rotateSwitch.state = true;
                                }
                            });
                            Action action = Actions.delay(3f, delayedAction);
                            rotateSwitchDoor.addAction(action);
                        }
                    }
                }
                if (rotateSwitchFrag != null) {
                    if (rotateSwitchFrag.triggerState == true) {
                        ActConstants.fragState = true;
                        rotateSwitchFrag.state = false;

                        Action delayedAction = Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                rotateSwitch rotateSwitch = ((rotateSwitch) ActConstants.publicInformation.get("rotateSwitchFrag"));
                                rotateSwitch.state = true;
                            }
                        });
                        Action action = Actions.delay(3f, delayedAction);
                        rotateSwitchFrag.addAction(action);
                    }
                }
            }
            if (event.getKeyCode() == Input.Keys.NUM_1) {
                ActConstants.currentSkillGroup = 1;

            }
            if (event.getKeyCode() == Input.Keys.NUM_2) {
                ActConstants.currentSkillGroup = 2;

            }

            if (event.getKeyCode() == Input.Keys.NUM_3) {
                ActConstants.currentSkillGroup = 3;

            }

            if (event.getKeyCode() == Input.Keys.Q) {
                ActConstants.physicalActionList.add(new ReverseMainCharacterGravity());
            }

            //Save the Game
            if (event.getKeyCode() == Input.Keys.P) {
                Beacon beacon = ((Beacon) ActConstants.publicInformation.get("Beacon"));
                beacon.setBeacon();
            }

            if (event.getKeyCode() == Input.Keys.ESCAPE) {
                Gdx.app.exit();
            }

            if(event.getKeyCode() == Input.Keys.R){
                synchronized (ActConstants.physicalActionListLock){
                    ActConstants.physicalActionList.add(new CreateStone(((MyStage)ActConstants.publicInformation.get("CurrentStage")).world));
                }
            }

            //return false;//不知道干嘛的
        }
        return super.keyUp(event, keycode);
    }


    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        if(!ActConstants.MainCharacterState.get("repulse")){
            if((event.getKeyCode()==Input.Keys.D&& ActConstants.MainCharacterState.get("goRight"))||
                    ((event.getKeyCode()==Input.Keys.A&& ActConstants.MainCharacterState.get("goLeft")))){
                ActConstants.MainCharacterState.replace("goRight",false);
                ActConstants.MainCharacterState.replace("goLeft",false);
               // ActConstants.MainCharacterState.replace("noControl",true);
                ((MainCharacter) ActConstants.publicInformation.get("MainCharacter")).getMySimulation().setLinearVelocity(0, ((MainCharacter) ActConstants.publicInformation.get("MainCharacter")).getMySimulation().getLinearVelocity().y);
            }

        }


        return super.keyUp(event, keycode);
    }



    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {//这里鼠标位置应该是像素个数，在desktopluncher里设定1900,1000的情况下基本和最右边是1900，最上边是100
        //当对话框调整的时候，在最左边和最上边的最大值会相应改变
        //System.out.println(x+"   "+y);

        return super.mouseMoved(event, x, y);
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

//        MoveLauncher moveLauncher = (MoveLauncher) ActConstants.publicInformation.get("moveLauncher");
//        moveLauncher.startMove();
//        moveLauncher.start();

        synchronized (ActConstants.physicalActionListLock){
            //button 0左键 1右键
            if(ActConstants.skillGroups[ActConstants.currentSkillGroup]!=null){

                if(button==1){
                    ActConstants.skillGroups[ActConstants.currentSkillGroup].skill1(x,y);
                }else{
                    ActConstants.skillGroups[ActConstants.currentSkillGroup].skill2(x,y);
                }

                // ((MonsterA)ActConstants.publicInformation.get("Monster0")).start();
            }
        }

        System.out.println("mousepoint   "+x/50+"   "+y/50);



        return super.touchDown(event, x, y, pointer, button);
    }
}
