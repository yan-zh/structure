package com.mygdx.game.Listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.NormalActors.MainCharacter;
import com.mygdx.game.Level2.NormalActors.MonsterA;

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
            if(event.getKeyCode()==Input.Keys.NUM_1){
                ActConstants.currentSkillGroup=1;
                System.out.println("1 put");
            }
            if(event.getKeyCode()==Input.Keys.NUM_2){
                ActConstants.currentSkillGroup=2;
                System.out.println("2 put");
            }
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
            System.out.println("鼠标点击");
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
