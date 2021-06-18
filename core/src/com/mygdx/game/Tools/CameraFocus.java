package com.mygdx.game.Tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.abstraction.MainCharacter;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.abstraction.MyStage;

public class CameraFocus {
    OrthographicCamera camerab2d;
    OrthographicCamera camera;
    MainCharacter mainCharacter;
    public float leftMouse;//鼠标位置与左边界的距离,使用像素坐标，界面是1900宽，1000高
    public float rightMouse;
    public float upMouse;
    public float downMouse;
    public int state;

   
    //物理世界和皮肤的摄像头错位了，不知道为什么，但是好像不影响皮肤的显示和动作的流畅
    public CameraFocus(OrthographicCamera camerab2d, OrthographicCamera camera){
        this.mainCharacter = (MainCharacter) ActConstants.publicInformation.get("MainCharacter");
        this.camerab2d = camerab2d;
        leftMouse = rightMouse = upMouse = downMouse = 50;
        state = ActConstants.CameraCharacterControl;
        this.camera = camera;




    }

    public void focusOn(float x, float y, float rate){
        camerab2d.translate((x-camerab2d.position.x)/rate,(y-camerab2d.position.y)/rate);
        camera.position.set(camerab2d.position.x* ActConstants.worldSize_pAndPhysic,camerab2d.position.y* ActConstants.worldSize_pAndPhysic,0);

        // System.out.println("physics camera x"+camerab2d.position.x);
        //System.out.println("physics camera y"+camerab2d.position.y);
        // System.out.println("physics character x"+characterMain.getPositionInSimulation().x);
        // System.out.println("physics character y"+characterMain.getPositionInSimulation().y);
    }

    public void directFocusOn(float x, float y){
        camerab2d.translate((x-camerab2d.position.x),(y-camerab2d.position.y));
        camera.position.set(camerab2d.position.x* ActConstants.worldSize_pAndPhysic,camerab2d.position.y* ActConstants.worldSize_pAndPhysic,0);

    }


    public void innerBoundary(float width, float height, float positionX, float positionY){


        //前两个是矩形的宽高（物理世界坐标），后来两个事矩形中心在物理世界坐标下在相机框的相对位置
        Vector2 characterPosition  = mainCharacter.getPositionInSimulation();
        Vector2 cameraPosition = new Vector2(camerab2d.position.x,camerab2d.position.y);


        // if(characterMain.getPositionInSimulation().x-cameraPosition.x>width/2f){
        //     camerab2d.position.set(characterPosition.x,cameraPosition.y,0);
        // }

//        if(!(((MyStage)MyGdxGame.currentStage).cUp<cameraPosition.y||((MyStage)MyGdxGame.currentStage).cDown>cameraPosition.y)) {
//            float upDifference = characterPosition.y - (height / 2 + positionY + cameraPosition.y - 10);//主角位置-方形上边界
//            if (upDifference > 0) {
//                camerab2d.translate(0, upDifference);//调整物理世界摄像机
//                camera.position.set(camerab2d.position.x * 50f, camerab2d.position.y * 50f, 0);//调整内存图片摄像机
//            }
//
//            float downDifference = characterPosition.y - (-height / 2 + positionY + cameraPosition.y - 10);//主角位置-方形下边界
//            if (downDifference < 0) {
//                camerab2d.translate(0, downDifference);
//                camera.position.set(camerab2d.position.x * 50f, camerab2d.position.y * 50f, 0);
//            }
//        }


        if(!(((MyStage)MyGdxGame.currentStage).cUp<cameraPosition.y)){
            float upDifference = characterPosition.y - (height / 2 + positionY + cameraPosition.y - 10);//主角位置-方形上边界
            if (upDifference > 0) {
                camerab2d.translate(0, upDifference);//调整物理世界摄像机
                camera.position.set(camerab2d.position.x * 50f, camerab2d.position.y * 50f, 0);//调整内存图片摄像机
            }
        }

        if(!(((MyStage)MyGdxGame.currentStage).cDown>cameraPosition.y)){
            float downDifference = characterPosition.y - (-height / 2 + positionY + cameraPosition.y - 10);//主角位置-方形下边界
            if (downDifference < 0) {
                camerab2d.translate(0, downDifference);
                camera.position.set(camerab2d.position.x * 50f, camerab2d.position.y * 50f, 0);
            }
        }




        float leftDifference = characterPosition.x-(cameraPosition.x-19+positionX-width/2);//主角位置-方形左边界
        if(leftDifference<0){
            camerab2d.translate(leftDifference,0);
            camera.position.set(camerab2d.position.x*50f,camerab2d.position.y*50f,0);
        }

        float rightDifference = characterPosition.x-(cameraPosition.x-19+positionX+width/2);//主角位置-方形右边界
        if(rightDifference>0){
            //camera.translate(rightDifference*50f,0);
            camerab2d.translate(rightDifference,0);
            //System.out.println("b2d x - camera/50 1  "+(camerab2d.position.x-camera.position.x/50f));
            camera.position.set(camerab2d.position.x*50f,camerab2d.position.y*50f,0);
            //System.out.println("b2d x - camera/50  "+(camerab2d.position.x-camera.position.x/50f));

        }





    }

    public void LRBoundary(float width, float height, float positionX, float positionY){
        Vector2 characterPosition  = mainCharacter.getPositionInSimulation();
        Vector2 cameraPosition = new Vector2(camerab2d.position.x,camerab2d.position.y);

        float leftDifference = characterPosition.x-(cameraPosition.x-19+positionX-width/2);//主角位置-方形左边界
        if(leftDifference<0){
            camerab2d.translate(leftDifference,0);
            camera.position.set(camerab2d.position.x*50f,camerab2d.position.y*50f,0);
        }

        float rightDifference = characterPosition.x-(cameraPosition.x-19+positionX+width/2);//主角位置-方形右边界
        if(rightDifference>0){
            //camera.translate(rightDifference*50f,0);
            camerab2d.translate(rightDifference,0);
            //System.out.println("b2d x - camera/50 1  "+(camerab2d.position.x-camera.position.x/50f));
            camera.position.set(camerab2d.position.x*50f,camerab2d.position.y*50f,0);
            //System.out.println("b2d x - camera/50  "+(camerab2d.position.x-camera.position.x/50f));

        }
    }





}
