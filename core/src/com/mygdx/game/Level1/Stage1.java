package com.mygdx.game.Level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.Constants.ActConstants;
import com.mygdx.game.Level2.Stage2;
import com.mygdx.game.MyGdxGame;


public class Stage1 extends Stage {//有一些按钮和背景，是类似开始界面

    //按钮和背景
    ImageButton startButton;
    Image backGround;


    public Stage1(InputMultiplexer inputMultiplexer){

        inputMultiplexer.addProcessor(this);//通过这个接受来自用户的操作信号

        //做了个背景和按钮（以后所有的素材，包括音乐，图片，动画都通过Asset类获取，后面会有Asset类的例子）
        Texture temp = new Texture(Gdx.files.internal("startStageBackGround and startButton.jpg"));//texture是图片类
        TextureRegion temp2 = new TextureRegion(temp, 0, 0, 430, 320);//textureRegion就是切割texture用的,好像图片坐标系起点是左上角
        backGround = new Image(temp2);//image和texture区别？？

        backGround.setSize(ActConstants.SCREEN_WIDTH, ActConstants.SCREEN_HEIGHT);
        backGround.setPosition(0, 0);//舞台坐标是左下角


        //按钮
        TextureRegion temp3 = new TextureRegion(temp, 620, 0, 70, 25);//�и�ͼƬ

        startButton = new ImageButton(new TextureRegionDrawable(temp3),new TextureRegionDrawable(temp3));//һ���ǵ����ͼƬ��һ���ǰ��µ�ͼƬ
        startButton.setSize(300, 150);
        startButton.setPosition(0, 0);

        //按钮监听
        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               // 在适当的条件（这里是按钮被按下），切换舞台，用下一个舞台换掉MyGdxGame的currentStage
                MyGdxGame.currentStage = new Stage2(ActConstants.inputMultiplexer);

                //注意每次切换舞台时把旧舞台dispose了，清空它占用的资源，主要是这个舞台用到的Asset
                return false;
            }
        });

        this.addActor(backGround);
        this.addActor(startButton);


      //  this.addListener(new MyInputListener1());//这个监听是对从最外层传入的输入信息的响应,可以多个舞台各自new一个同一个类的对象，都有效果
        //****************注意，这个监听就算这个舞台没运行act也有效果
    }

}
