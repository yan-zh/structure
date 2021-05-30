package com.mygdx.game.Listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level1.Stage1;
import com.mygdx.game.Level2.NormalActors.*;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.MyGdxGame;

public class UserInputListener extends InputListener {

    public UserInputListener(){
    }

    public boolean keyDown(InputEvent event, int keycode) {
        if(!ActConstants.MainCharacterState.get("repulse")){
            if(event.getKeyCode()==Input.Keys.D) {
                ActConstants.MainCharacterState.replace("goRight",true);
                ActConstants.MainCharacterState.replace("goLeft",false);
                //ActConstants.MainCharacterState.replace("noControl",false);
            }
            if(event.getKeyCode()==Input.Keys.A) {
                ActConstants.MainCharacterState.replace("goRight",false);
                ActConstants.MainCharacterState.replace("goLeft",true);
                //ActConstants.MainCharacterState.replace("noControl",false);
            }
            if(event.getKeyCode()==Input.Keys.W&& ActConstants.MainCharacterState.get("onGround")) {

                ((MainCharacter) ActConstants.publicInformation.get("MainCharacter")).jump();
                ActConstants.MainCharacterState.replace("onGround",false);

            }
            if(event.getKeyCode()==Input.Keys.SPACE) {
                MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));
                System.out.println(mainCharacter.mySimulation.getPosition().x + "," + mainCharacter.mySimulation.getPosition().y);
            }
            //Portal Listener
            if(event.getKeyCode()==Input.Keys.K) {

                Portal portal =(Portal)ActConstants.publicInformation.get("Portal");
                if(portal != null){
                    MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));
                    if(portal.triggerState == true && portal.isActive == true)
                    {

                        portal.state = false;

                        Action delayedAction = Actions.run(new Runnable() {
                            @Override
                            public void run() {
//                            ((Portal)ActConstants.publicInformation.get("Portal")).state = true;
                                Portal portal =(Portal)ActConstants.publicInformation.get("Portal");
                                MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));
                                mainCharacter.mySimulation.setTransform(new Vector2(portal.dstXPos, portal.dstYPos), 0);
                                Action delayedAction = Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
//                            ((Portal)ActConstants.publicInformation.get("Portal")).state = true;
                                        Portal portal =(Portal)ActConstants.publicInformation.get("Portal");
                                        Stage2 stage2 = (Stage2)ActConstants.publicInformation.get("stage2");
                                        portal.state = true;
                                        if(portal.Stagetranto == "Stage1")
                                        {
                                            stage2.dispose();
                                            MyGdxGame.currentStage = new Stage1(ActConstants.inputMultiplexer);
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


                    }
                }

            }
            //rotateSwitch
            if(event.getKeyCode()==Input.Keys.E) {
                rotateSwitch rotateSwitch =  ((rotateSwitch)ActConstants.publicInformation.get("rotateSwitch"));
                MainCharacter mainCharacter = ((MainCharacter)ActConstants.publicInformation.get("MainCharacter"));

                if(rotateSwitch != null)
                {
                    if(rotateSwitch.triggerState == true)
                    {
                        Portal portal =(Portal)ActConstants.publicInformation.get("Portal");
                        if(portal != null && rotateSwitch.ControlType == "portal")
                        {portal.turnOn();
                            Action delayedAction = Actions.run(new Runnable() {
                                @Override
                                public void run() {
//                            ((Portal)ActConstants.publicInformation.get("Portal")).state = true;
                                    rotateSwitch rotateSwitch =  ((rotateSwitch)ActConstants.publicInformation.get("rotateSwitch"));
                                    rotateSwitch.state = true;
                                }
                            });
                            Action action = Actions.delay(3f, delayedAction);
                            portal.addAction(action);}

                        Door door = ((Door) ActConstants.publicInformation.get("door"));
                        System.out.println(rotateSwitch.ControlType);
                        if(door!=null  && rotateSwitch.ControlType == "door")
                        {
                            rotateSwitch.state = false;
                            door.turnOn();
                            door.state = false;
                            Action delayedAction = Actions.run(new Runnable() {
                                @Override
                                public void run() {
//                            ((Portal)ActConstants.publicInformation.get("Portal")).state = true;
                                    Door door =  ((Door)ActConstants.publicInformation.get("door"));
                                    door.state = true;
                                }
                            });
                            Action action = Actions.delay(3f, delayedAction);
                            rotateSwitch.addAction(action);}
                        }
}


                }
            }
            if(event.getKeyCode()==Input.Keys.NUM_1){
                ActConstants.currentSkillGroup=1;

            }
            if(event.getKeyCode()==Input.Keys.NUM_2){
                ActConstants.currentSkillGroup=2;

            }



        return super.keyUp(event, keycode);
        //return false;//不知道干嘛的
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
        //button 0左键 1右键
        if(ActConstants.skillGroups[ActConstants.currentSkillGroup]!=null){

            if(button==1){
                ActConstants.skillGroups[ActConstants.currentSkillGroup].skill1(x,y);
            }else{
                ActConstants.skillGroups[ActConstants.currentSkillGroup].skill2(x,y);
            }

           // ((MonsterA)ActConstants.publicInformation.get("Monster0")).start();
        }
        return super.touchDown(event, x, y, pointer, button);
    }
}
