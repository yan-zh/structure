package com.mygdx.game;

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
/*这*************************************************这个是监听换舞台的版本
public class Stage0 extends Stage {//有一些按钮和背景，是类似开始界面
    ImageButton startButton;
    Image backGround;


    public Stage0(final MyGdxGame myGdxGame){

        //做了个背景和按钮
        Texture temp = new Texture(Gdx.files.internal("startStageBackGround and startButton.jpg"));//texture是图片类
        TextureRegion temp2 = new TextureRegion(temp, 0, 0, 430, 320);//textureRegion就是切割texture用的,好像图片坐标系起点是左上角
        backGround = new Image(temp2);//image和texture区别？？

        backGround.setSize(PublicData.SCREEN_WIDTH, PublicData.SCREEN_HEIGHT);
        backGround.setPosition(0, 0);//舞台坐标是左下角


        //���ð�ť
        TextureRegion temp3 = new TextureRegion(temp, 620, 0, 70, 25);//�и�ͼƬ

        startButton = new ImageButton(new TextureRegionDrawable(temp3),new TextureRegionDrawable(temp3));//һ���ǵ����ͼƬ��һ���ǰ��µ�ͼƬ
        startButton.setSize(300, 150);
        startButton.setPosition(0, 0);


        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                myGdxGame.changeStage(PublicData.Stage1);
                return false;
            }
        });

        this.addActor(backGround);
        this.addActor(startButton);
    }
}
*/

public class Stage0 extends Stage {//有一些按钮和背景，是类似开始界面
    ImageButton startButton;
    Image backGround;


    public Stage0(InputMultiplexer inputMultiplexer){

        inputMultiplexer.addProcessor(this);//加入监听

        //做了个背景和按钮
        Texture temp = new Texture(Gdx.files.internal("startStageBackGround and startButton.jpg"));//texture是图片类
        TextureRegion temp2 = new TextureRegion(temp, 0, 0, 430, 320);//textureRegion就是切割texture用的,好像图片坐标系起点是左上角
        backGround = new Image(temp2);//image和texture区别？？

        backGround.setSize(PublicData.SCREEN_WIDTH, PublicData.SCREEN_HEIGHT);
        backGround.setPosition(0, 0);//舞台坐标是左下角


        //���ð�ť
        TextureRegion temp3 = new TextureRegion(temp, 620, 0, 70, 25);//�и�ͼƬ

        startButton = new ImageButton(new TextureRegionDrawable(temp3),new TextureRegionDrawable(temp3));//һ���ǵ����ͼƬ��һ���ǰ��µ�ͼƬ
        startButton.setSize(300, 150);
        startButton.setPosition(0, 0);


        startButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                PublicData.currentStage = PublicData.Stage1;

                return false;
            }
        });

        this.addActor(backGround);
        this.addActor(startButton);


      //  this.addListener(new MyInputListener1());//这个监听是对从最外层传入的输入信息的响应,可以多个舞台各自new一个同一个类的对象，都有效果
        //****************注意，这个监听就算这个舞台没运行act也有效果
    }
}
