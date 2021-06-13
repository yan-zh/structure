package com.mygdx.game.Level1.NormalActors;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Constants.ActConstants;

public class ChangeCamera {


    OrthographicCamera cameraPhysic;//物理世界相机
    OrthographicCamera stageCamera;//舞台用的摄像机

    int state;
    int count;

    public ChangeCamera(OrthographicCamera cameraPhysic, OrthographicCamera stageCamera) {
        this.cameraPhysic = cameraPhysic;
        this.stageCamera = stageCamera;
        this.state = 1;
        this.count = 0;
    }


    public void close(){
        state = 1;
        count = 0;
    }

    public void leave(){
        state = 0;
        count = 0;
    }

    public void act(){

        if(count==0){
            if(state==1){
                cameraPhysic.setToOrtho(false, (ActConstants.SCREEN_WIDTH)/ ActConstants.worldSize_pAndPhysic,(ActConstants.SCREEN_HEIGHT)/ ActConstants.worldSize_pAndPhysic);//这个是摄像机的框的大小，摄像机取景后还要经过最外层界面的压缩得到最终效果
                stageCamera.setToOrtho(false, ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);
            }else if(state==0){
                cameraPhysic.setToOrtho(false, (ActConstants.SCREEN_WIDTH*3)/ ActConstants.worldSize_pAndPhysic,(ActConstants.SCREEN_HEIGHT*3)/ ActConstants.worldSize_pAndPhysic);//这个是摄像机的框的大小，摄像机取景后还要经过最外层界面的压缩得到最终效果
                stageCamera.setToOrtho(false, ActConstants.SCREEN_WIDTH*3, ActConstants.SCREEN_HEIGHT*3);
            }

            count=1;
        }





    }
}
